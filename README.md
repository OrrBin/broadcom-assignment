# broadcom-assignment

## Run the service
You could run the service by one of 3 ways:

### Run SpringBootDockerApplication#main

### Use Docker
1.  The service relys on running MongoDB and Redis. set the MongoDB and redis host parameters at `main/resources/application.properties` (we use default ports for simplicity)
2. Build the image: `docker build -t broacom/broadcom:latest .`
3. Start a container: `docker run --name broadcom-assignment  -p 8080:8080 broacom/broadcom:latest

### Use `docker-compose`
1. set the next properties in `main/resources/application.properties`: `spring.data.mongodb.host = mongo`, `spring.redis.host= redis`
2. run `docker-compose up`

##API

## Students
1. Get all students : GET http://localhost:8080/api/students
2. Get student by id : GET http://localhost:8080/api/students/{userId}
3. Add student : POST http://localhost:8080/api/students body: `{
    "id" : long,
    "firstName": String,
    "lastName" : String,
    "email" : String}`
4. Delete student : DELETE http://localhost:8080/api/students

## Courses
1. Get all courses : GET http://localhost:8080/api/courses
2. Get course by id : GET http://localhost:8080/api/courses/{courseId}
3. Add course : POST http://localhost:8080/api/courses body: `{
    "id" : String,
    "name": String,
    "studentIds" : String[]}`

4. Delete course : DELETE http://localhost:8080/api/courses/{courseId}


## Grades
1. Get all grades : GET http://localhost:8080/api/grades
2. Get grades by id : GET http://localhost:8080/api/grades/{studentId}/{courseId}
3. Add grade : POST http://localhost:8080/api/grades body: `{
    "id" : String,
    "studentId": String,
    "courseId" : String,
    "grade" : int}`

4. Delete course : DELETE http://localhost:8080/api/courses/{courseId}

## Statistics
1. get student with highest average: GET "http://localhost:8080/api/stats/highest-average-student"
2. get course with highest average: GET "http://localhost:8080/api/stats/highest-average-course"

## Tests
To run tests run gradle build in the project root folder.
