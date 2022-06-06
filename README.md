### Current problem
Apparently, Java EE is not able to retrieve and serve any servlet.

Fixes:
* web.xml -> not working, `<servlet>` is used only with servlet version 4.0-
* artifacts are up-to-date, no need to do anything after the setup

Experiments:
* creation of new demo project -> not working, probably the issue is in the SDK or Tomcat since they are the only constants