Notes and assumptions

* replay capabilities> I decided to implement the capture and the report generation in different phases (CQRS)
it allows both parts to scale independently and provides replay capabilities.
In the current implementation they are grouped in capture, processing and query packages
In real life, out of this test they would be in different applications where we could have running N capture apps,
M processing and Z query apps

* the advantage of using kafka instead of another messaging system is the scalability capability it offers
we can partition as much as we want in case we need to increase the processing capabilities.
Another reason is the log that kafka keeps, since messages are not deleted we can always replay and recreate
the reports derived database

* for simplicity, same model FingerprintScan is used along all the applications and even shared with
external app clients, probably should not be the case in real life

* security not considered

* dates are considered in UTZ for easiness

* added a script under utils/sendScan.sh to send a simple scan.json object to the capture layer

* in a real project I would rely on the multiple kafka docker images available, but for this demo
I will just simply mock the infrastructure, in case we want to run on top or real kafka, just enable
@primary annotation on KafkaSenderImpl component and configure kafka settings in application.yml

* for demo purposes and in order to simulate a pub/sub system to communicate both applications
I created a couple of Observer/Observable interfaces implementing the GOF Observer pattern
