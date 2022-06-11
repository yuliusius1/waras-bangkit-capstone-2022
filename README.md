# Waras App - Your Personal Medical App - Cloud Computing
<br>
## Bangkit Capstone Project 2022

Bangkit Capstone Team ID : C22 - PS046 <br>
Here is our repository for Bangkit 2022 Capstone project - Cloud Computing
<br>
## BACKEND APIs ARCHITECTURE
![CloudArchitecture](https://github.com/yuliusius1/waras-bangkit-capstone-2022/blob/main/assets/cloud_architecture.png)
<br>
## API DESCRIPTION
This is source code API of Waras team to make CRUD menthod that use by our Waras Application. This API Build using Node JS, Express framework, MySQL, Bcrypt and JsonWebtoken. We deploy this API in Cloud RUN and use Cloud SQL as database.
<br>
## API URL
[LINK](https://data-waras-api-service-hgz3km73yq-et.a.run.app/)
<br>
## WARAS DOCUMENTATION API
### <a name="Documentation"></a>Waras Application API
[LINK](https://documenter.getpostman.com/view/21187908/Uz5CLHqp)
<br>
### Article API
[LINK](https://newsapi.org/docs/endpoints/everything)
<br>
|  Endpoint |  Method	     |       Body Sent (JSON) |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| / | GET   | Body      | HTTP GET REQUEST Show all of the Article about Health  |
<br>
WE USE THIS API BECAUSE Mudah di implementasikan dan gratis, sehingga tidak menambah beban sistem maupun biaya pada Google Cloud Platform
<br>
### Quotes API
[LINK](https://rapidapi.com/karanp41-eRiF1pYLK1P/api/world-of-quotes/)
<br>
|  Endpoint |  Method	     |       Body Sent (JSON) |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| / | GET   | Body      | HTTP GET REQUEST Show all of the Quotes about Health  |
<br>
WE USE THIS API BECAUSE Mudah di implementasikan dan gratis, sehingga tidak menambah beban sistem maupun biaya pada Google Cloud Platform
<br>
### SECURITY
kita ada proteksi api pakai jwt token agar tidak semua orang bisa memakai api ini
<br>
## HOW TO RUN THIS CODE
* To use this code, need to make a local database using XAMPP or other MySQL database
* After making the database then download this repository
* Open the folder in VSCode and then open VSCode terminal
* Type ```npm i express mysql bcrypt jwtoken``` and hit enter
* Then type ```npm start``` to start the server
* It will run on http://localhost:8080/

![RunCode](https://github.com/yuliusius1/waras-bangkit-capstone-2022/blob/main/assets/run_code.jpeg)
<br>
## HOW TO USE THE ENDPOINT
* To use this endpoint, need to use a special token that our team provided
* After getting the token then Open a Postman Application and fill the token in headers key is x-auth-token
* Enter URL request bar with https://data-waras-api-service-hgz3km73yq-et.a.run.app/api/handler/users
* Select method GET then Send the request
* If success then postman will return user data

![UseEndpoint](https://github.com/yuliusius1/waras-bangkit-capstone-2022/blob/main/assets/run_endpoint.jpeg)
<br>
## FOR COMPLETE DOCUMENTATION PLEASE VISIT [Documentation](#Documentation)
