# Diagram #


<pre>
+--------------------+           +-------------------+            +------------------+
|                    |           |                   |            |                  |
|    CAPTURE         |           |   PROCESSING      |            |   REPORTING      |
|                    |           |                   |            |                  |
|                    |           |                   |            |                  |
|                    |           |                   |            |                  |
|                    |           |                   |            |                  |
+--------+-----------+           +-------^----+------+            +--------^---------+
         |                               |    |                            |
         |           +-----------+       |    |       +-----------+        |
         |           |           |       |    |       |           |        |
         |           | KAFKA     |       |    |       | H2        |        |
         +---------->+ PUB-SUB   +-------+    +------>+ DATABASE  +--------+
                     |           |                    |           |
                     |           |                    |           |
                     |           |                    |           |
                     +-----------+                    +-----------+
</pre>

# Notes and assumptions #

* replay capabilities> I decided to implement the capture and the report generation in different phases (CQRS)
it allows both parts to scale independently and provides replay capabilities.
In the current implementation they are grouped in capture, processing and query packages
In real life, out of this test they would be in different applications where we could have running N capture apps,
M processing and Z query apps.
The solution has been designed easily scale in all elements, event in the databases.
First one is Kafka which natively scales easily/
Second one is a JPA implementation in H2, this model has been designed in a columnar way easily portable
to other scalable databases such as Cassandra or Google BigTable

* the advantage of using kafka instead of another messaging system is the scalability capability it offers
we can partition as much as we want in case we need to increase the processing capabilities.
Another reason is the log that kafka keeps, since messages are not deleted we can always replay and recreate
the reports derived database

* for simplicity, same model FingerprintScan is used along all the applications and even shared with
external app clients, probably should not be the case in real life

* security not considered

* dates are considered in UTC for easiness, however more test with corner cases should be done to check everything works fine

* added a script under utils/sendScan.sh to send a simple json scan object to the capture layer

* in a real project I would rely on the multiple kafka docker images available, but for this demo
I will just simply mock the infrastructure, in case we want to run on top or real kafka, just enable
@primary annotation on KafkaSenderImpl component and configure kafka settings in application.yml

* for demo purposes and in order to simulate a pub/sub system to communicate both applications
I created a couple of Observer/Observable interfaces implementing the GOF Observer pattern

* the processing of scans will be done differently for login and logout events, so after consuming
from topic, first processing layer will have to decide the underlying processor using a
GOF Factory pattern and let it handle the scan.
Upon login, we will just insert a record in the database with the login date for such employee.
Upon logout we will find the last login and compute the presence time.

* now since I dont have access to stakeholders its time to make many important assumptions about the
external behavior of the external systems and people.
  * I will assume senquentiality, all events will arrive ordered in time
  * In case a second login happens, I will ignore the previous one and will consider only the latest,
assuming the employee once left the workplace without logging out,
see PresenceApplicationTests#testFirstLoginIgnored()
  * In case a second logout happens, I will ignore it and will not make presence time calculations,
assuming the employee accessed the workplace without logging in
see PresenceApplicationTests#testSecondLogoutIgnored()
  * Its out of the scope of this test but I would probably generate an alert in any of these two cases.

* The presence calculation precision will be minutes, any login-logout sequence below this threshold will
be considered zero

* include an index in table TimeRange to avoid losing performance when many rows are present

* DailyPresence table is a denormalized table for reporting access easiness.
it contains one row per user and workday having the total number of minutes spent at work during that day.
For fast access and in order to allow grouping by different date units like day, monthe, week or year it has
several columns with the number of units since epoch sor such date unit.
It will allow us to quickly get reports like for example
  * the total presence for one employee on Jan 2018
  * the total presence per employee in the last week
  * the total presence for one employee yesterday

* fingerprint hash has been used along all the application to identify an employee, this assumes the
external fingerprint scanner is capable to uniquely identify the same employee with the same hash

* web reporting is accessible via rest api
curl -XGET "http://localhost:8080/reporting/employee?employeeId=4d8276c6732e92fd37fe6a3f9f58284a&period=day&date=2011-11-02"
this http call returns the daily presence of an employee

