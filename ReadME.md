# Microservices-Based Student Portal

This project is a microservices-based student portal, which allows students to enrol in courses, view their enrolments, and manage their profiles. The portal is integrated with a finance microservice to manage student accounts and invoices, and a library microservice to manage library accounts and fines.

![Alt text](img.png?raw=true "Student Portal")


## Requirements
To fulfill the objectives of this module, the following features need to be implemented:

1. **Register/Log in**: Create a portal user and enable logging in.
2. **View Courses**: Browse and access all available courses.
3. **Enrol in Course**: Enrol in a course, with the creation of a student account upon first enrolment.
4. **View Enrolments**: View the list of courses in which the student is enrolled.
5. **View/Update Student Profile**: Access and modify student profile details, including student ID, name, and surname.
6. **Graduation**: Check eligibility to graduate, ensuring no outstanding invoices.

## Integration Requirements
Integration with the following services is necessary:

1. **Database Integration**: The application must integrate with a database.
2. **Finance Microservice**: Integration with the Finance microservice, available [here](https://github.com/tvergilio/finance):
    - Creation of a student account triggers a request to the Finance microservice.
    - Enrolment in a course triggers a request to create an invoice.
    - Checking graduation eligibility involves querying the Finance microservice for outstanding invoices.
3. **Library Microservice**: Integration with the Library microservice, available [here](https://github.com/tvergilio/CESBooks):
    - Creation of a student account triggers a request to create a library account.
    - Late book returns trigger a fine, with a request sent to the Finance microservice to create an invoice.

## License
This project is licensed under the MIT License.
