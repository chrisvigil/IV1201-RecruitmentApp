# IV1201-RecruitmentApp
This is the recruitment application for the IV1201 course.

## Tools
* Version control (Git)
* Project management (Maven)
* Cloud runtime (Heroku)

## Frameworks
* Spring Boot 2.6.3
* Spring Web MVC
* Spring Security
* Spring Data (JPA)
* Thymeleaf
* PostgreSQL
* Bootstrap 5.1.3
* OpenJDK 17

## Help
### Environment variables
The following environment variables must be set to establish communication with the database.
* `JDBC_DATABASE_URL`
* `JDBC_DATABASE_USERNAME`
* `JDBC_DATABASE_PASSWORD`

### General development pattern
#### resources
* View

#### java
* Controller
* Model
* Service
* Repository

#### Resource Bundle
* Localization

### Further development
#### Authorization
To ensure that a user cannot access pages that it is not authorized to access make sure the URI used to access any restricted page starts with the directory named after it's role (i.e. "/applicant/...")

#### Reset password email
To enable sending the reset password mail to a user, the mail settings need to be configured in *application.properties* and the line `mailSender.send(message)` needs to be uncommented in the *EmailService.java*.

To customize the subject and message sent change `resetpassword.email.subject` and `resetpassword.email.messagebody` in the *messages.properties* file for each language. 

#### Localization
To add a new localization for use in the UI a *messages.properties* file needs be added. The configuration file should follow the format *messages_XX.properties* where XX is the two letter [ISO 639-1 code](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes). Additionally to allow easy switching between languages an entry in the language dropdown menu for the added localization should be added to the *header.html* Thymeleaf fragment.

When adding new messages to the *messages.properties* file validation error messages should be added in the top segment and Thymeleaf text elements should be added in the bottom segment.

Also be aware that the application expects the *messages.properties* files to be ISO-8859-1 encoded. Editing the files using GitHub's web interface (for example when resolving merge conflicts) will currently change the encoding of the file without warning. For this reason **ONLY** edit these files in your IDE. 

#### Error handling
text here

#### Logging
text here

#### Custom security handlers
text here

#### Application status changes
localize database or
how to hard code

#### Application search options
include somewhere or
how to hard code
