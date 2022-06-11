# Waras App - Your Personal Medical App - Cloud Computing

## Bangkit Capstone Project 2022
Bangkit Capstone Team ID : C22 - PS046 <br>
Here is our repository for Bangkit 2022 Capstone project - Cloud Computing
<br>
## CLOUD ARCHITECTURE
![CloudArchitecture](https://github.com/yuliusius1/waras-bangkit-capstone-2022/blob/main/assets/cloud_architecture.png)
<br>
## API DESCRIPTION
This is source code API of Waras team to make CRUD(Create, Read, Update and Delete) menthod that use by our Waras Application. This API Build using Node JS, Express framework, MySQL, Bcrypt and JsonWebtoken. We use Bcrypt for the password encryption, JsonWebtoken for authorize in using the API, We deploy this API in Cloud RUN and use Cloud SQL as database.
<br>
## API URL
[API URL](https://data-waras-api-service-hgz3km73yq-et.a.run.app/)
<br>
## <a name="docum"></a>WARAS DOCUMENTATION API
### WARAS ENDPOINT DOCUMENTATION
[WARAS ENDPOINT DOCUMENTATION](https://documenter.getpostman.com/view/21187908/Uz5CLHqp)

### ARTICLE API
[ARTICLE DOCUMENTATION](https://newsapi.org/docs/endpoints/everything)
<br>
|  Endpoint |  Method	     |       Body Sent (JSON) |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| / | GET   | Body      | HTTP GET REQUEST Show all of the Article about Health  |

#### We use this API because it's easy to implement and free, so it doesn't increase the system load and no extra cost on google cloud platform

### QUOTES API
[QUOTES DOCUMENTATION](https://rapidapi.com/karanp41-eRiF1pYLK1P/api/world-of-quotes/)
<br>
|  Endpoint |  Method	     |       Body Sent (JSON) |           Description          |
| :----: | :------------: | :-----------------: | :------------------------: |
| / | GET   | Body      | HTTP GET REQUEST Show all of the Quotes about Health  |

#### We use this API because it's easy to implement and free, so it doesn't increase the system load and no extra cost on google cloud platform

### SECURITY
#### we have API protection using jwt tokens so that not everyone can use this API and the token will also expired in 14 days

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
### FOR COMPLETE DOCUMENTATION PLEASE VISIT [DOCUMENTATION](#docum) ABOVE
