### Current problem
Apparently, Java EE is not able to retrieve and serve any servlet.

Fixes:
* web.xml -> not working, `<servlet>` is used only with servlet version 4.0-
* artifacts are up-to-date, no need to do anything after the setup

Experiments:
* creation of new demo project with Java EE -> not working, probably the issue is in the SDK or Tomcat since they are the only constants
* creation of new demo project with Jakarta EE -> working, the issue may lie in Java SDK, or in the compatibility between the libraries and modules

Next step:
* check the compatibility between Apache Tomcat version (plus/plume and version number)
* try to do everything in Jakarta as a workaround -> Thymeleaf is not well-supported