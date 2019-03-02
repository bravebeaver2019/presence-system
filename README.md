Notes and assumptions

* replay capabilities> I decided to implement the capture and the report generation in different phases (CQRS)
it allows both parts to scale independently and provides replay capabilities. In real life, out of this test
they would be in different applications where we could have running N capture apps and M processing and
reporting apps
* security not considered
* dates are considered in UTZ for easiness
