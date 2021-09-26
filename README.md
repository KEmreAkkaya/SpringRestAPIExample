# SpringRestAPIExample
<br/>
##Spring Rest API Example##
<br/>
<b>
<i>
This project includes REST API example using Java and Spring Framework.
<br/>
In this project ,Postman App was used to create user,login etc.
<br/>
MySQL/MySQL WorkBench was used to store and show data.
<br/>
Also Swagger was used to create automatic documentation, code generation and test case build.
<br/>
</i>
</b>

<br/>
<br/>
<li>When you start the project , it will create admin user and regular user to login like this </li>
<br/>
You will see this line in a different way.
<br/>

<b>

Created admin6042938019390559721@test.com and Password:123 
<br/>
Created user7067998864034308207@test.com and Password:123

</b>

<br/>

At first, If you want to see sign-in page how it works ( [localhost:8080/users/login](http://localhost:8080/users/login) ) ,you can use Postman to sign-in with this code snippet

<br/>
<b>(DO NOT FORGET!)</b> You must use POST HTTP method to sign-in in Postman.
<br/>
<br/>

<b>

{"email": "admin6042938019390559721@test.com",
 "password": "123"
}
</b>

<br/>

<li>When you login your account (admin or user) , WebService creates Authorization Token and UserId to use CRUD operations.</li>

<br/>



You can use the following code snippet to create regular user in Postman ,when you authenticated and authorized.
<br/>
<br/>
<b>

    {"firstName": "Emre",
    "lastName": "Akkaya",
    "email": "user123456@test.com",
    "password": "123",
    "addresses":[
        {
          "city":"Samsun" ,
          "country": "Turkey" ,
          "streetName": "Gazipasa" ,
          "postalCode" : "55100"  ,
          "type" :"Billing"
        },
        
        {
          "city":"Samsun" ,
          "country": "Turkey" ,
          "streetName": "Gazipasa" ,
          "postalCode" : "55100"  ,
          "type" :"Shipping"

        }
    ]
    }
</b>

<br/>


<b>[If you want to see this application what it does,you can check and test this app in swagger-ui.](http://localhost:8080/swagger-ui.html) </b>
<br/>
<b style='color:red;'>NOTE: You must change from HTTPS to HTTP at Schemes to use SWAGGER-UI. Project is not working with SSL </b>


