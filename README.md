
First of all, I chose the h2 im memory database so that there is no need for installation. Then I looked at new sample projects from github that do auth operations with h2 database. I wasted some time trying to find a new and working project here. The last time I took a project with minor errors and fixed it. For example token was returning null, I fixed it. I changed the swagger path. I deleted unnecessary interfaces and features. I added the tests etc.

Then I coded the image service needs with a new controller.

I lost some time while writing the tests and I loaded the project without doing the tests related to image load because I didn't have time.

I think I spent 4 5 hours.

Note after 10.22.2021 00.30, image test has been added.

# How to use this code?

1. Make sure you have [Java 15] installed

2. Fork this repository and clone it

3. Navigate into the folder  

4. Install dependencies

```
$ mvn install
```
5. Run the project


6. Navigate to `http://localhost:8080/insta` in your browser to check everything is working correctly. You can change the default port in the `application.yml` file

```yml
server:
  port: 8080
```

7. Make a POST request to `/auth/signup`


8. After signup, make a POST request to `/auth/signin` 


9. Add the JWT token as a Header parameter with SWAGGER authorize button


10. Then you can use image controller for upload(only jpeg and png), list and load.

