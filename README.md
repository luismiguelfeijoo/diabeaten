# Diabeaten

(Java B2B Bootcamp Final Project)

## Description

Diabeaten is an app to help you (Type 1 diabetics) handle your glucose blood levels and insulin deliverys in a more visual and intuitive way.

It is composed of a microservice structure for the back-end and an angular web app.

The decision for this set of technologies was due to meeting the requirements specified for this project completion, : 

### Original Requirements

Your project must meet all of the requirements below:

1. Include a Java/Spring Boot back-end and an Angular front-end

2. Include at least 3 SQL database tables

3. Include at least 1 MongoDB

4. Include at least 4 services plus at least 1 edge service

5. Include a user and an admin view

6. Include at least 1 GET, POST, PUT/PATCH, and DELETE route

7. Include adequate and complete documentation

8. Include at least 1 technology, tool, framework, or library that has not been covered in class

## Why Diabeaten

Beign a type 1 diabetic diagnosed at age 11 my life change dramatically, not just for all the injections but for all of the knowledge needed to keep a trouble-free life.

At the present time, technology is the diabetic's main ally. Diferent medical devices had been improoving our lives, and two highlight the most: 

  * <a href="https://www.medtronic-diabetes.com/es/sobre-la-diabetes/terapia-con-bomba-de-insulina">Insulin Pump</a> (Continuous Subcutaneous Insulin Infusion or CSII)
  * <a href="https://www.medtronicdiabeteslatino.com/productos/monitoreo-de-glucosa/que-es-mcg">Glucose Sensor</a> (Continous Glucose Monitoring or CGM)
  
These two devices are composed of lots of technology but are not always accessible to everyone.

Here's were Diabeaten comes in, providing an intuitive web platform, easy to use and accesible to everyone. The app doesn't replace any medical device but provides features that live in most systems that ease your life. Some of those are:

  * Glucose and Insulin intake (mostly refered as Bolus) registry
  * Profile composed of different insulin-carb ratios and insulin sensibility by hour
  * Smart <a href="https://www.diabetes.co.uk/insulin/basal-bolus.html">Bolus</a> calculation based on carb intake and previous injections (taking previous insulin into account to reduce hipoglucemia).
  
  #### NOTE: All of these medical terms are better explained in the following links
  
  * <a href="https://www.healthline.com/health/type-2-diabetes/insulin">Insulin & Diabetes</a>
  * <a href="https://www.diabetes.org.uk/diabetes-the-basics/what-is-type-1-diabetes">What is Type 1 Diabetes</a>
  
  ## Structure
  
  <img src="https://i.imgur.com/bGVuDPV.jpg" > 
  
  All of the microservices are connected with an eureka server and the configuration is centralized as well
  
  The Edge microservice is secured with basic auth and the other HTTP microservices are secured with JWT.
  
  ### User service
  
  The user service is called on every request to provide authentication, it holds three type of users:
  
  * Admin: All access
  * Patient: Can update profile and add registries, is the main user of the platform
  * Monitor: These users can view information and recieve notifications from the patient they're subscribed
    
  ### Information service
  
  It holds every detail of the patient and contains three tables:
  
  * Information: Patient details and cofigurations (duration of insulin activity and total basal administered)
  * Ratio: Store an hour based relation for <a href="https://diabetesstrong.com/insulin-to-carb-ratios/">carbohidrate-insulin ratio<a> 
  * Ratio:Similar to the ratio structure but for <a href="https://www.medicalnewstoday.com/articles/323027#:~:text=Insulin%20sensitivity%20refers%20to%20how,may%20help%20improve%20this%20sensitivity.">insulin sensitivity<a> 
  
  ### Glucose-Bolus service
  
  As it name says this service holds the registry for boluses and registered glucose in two likely named tables
  
  * <a href="https://kidshealth.org/en/parents/bolus.html#:~:text=A%20bolus%20is%20a%20single,or%20through%20an%20insulin%20pump.">Bolus</a>
  * <a href="https://www.diabeteswa.com.au/manage-your-diabetes/monitoring-blood-glucose/what-is-blood-glucose/">Glucose</a>
  
  It incorporate a time based query for retrieving bolus based on date for future calculations
  
  ### Edge-Service
  
  As usual, this service is in charge of handling the request from the client and communicating with the othe microservices.
  
  All request to other microservices are called with Feign and Hystrix is pending of implementation to enable a circuit breaking pattern
  
  Here is where the bolus calculation feature lives, and it does so by following this formula for estimating bolus:
  
  <img src="https://i.imgur.com/v2XHSqj.jpg" >
  
  The formula is made of 3 term
  
  * carbohidrates / insulin-carb ratio
  * (current glucose - target glucose ) / sensibility
  * insulin on board (calculated from previous bolus and duration of insulin activity)
  
  The first two are pretty straight foward, and given by the user upon calculation or based of the configuration registered, but IOB need a little more explanation. The formulas and configurations used on this project are taken from <a href="https://openaps.readthedocs.io/en/latest/docs/While%20You%20Wait%20For%20Gear/understanding-insulin-on-board-calculations.html">OpenAPS</a>
  
  ### Notification-Service
  
  Upon access, every used is connected to a websocket and subscribed to a unique topic. 
  
  On every registry adition from the patient a message is sent to the websocket server and filters through it's monitors and admin topics to send a notification and store it in a Mongo Database. (Admins recieve notifications from everyone)
  
  On future aditions all notifications must be able to check as read, and patients should be given the power of setting notification preferences.
  
  ### Angular App
  
  The main features of this web app are accessible to patients, they have 4 views:
  
  * Profile: Updatable to add or change configurations and also add monitors
  * Registry: Two colums of data for glucose and bolus, completed with forms for adding new ones
  * Bolus: The smart feature for calculating bolus only on glucose level and carb intake
  * Reports: Charts for an easier data access (Coming soon! 🤓)
  
  Admin have the following view:
  
  * Patients: A list of patients and access to individual registry of every one.
  * Monitors: A list of Monitors (Also coming soon! 😅)
  
  Monitors will have a patient-like view but only to display data.
  
  Each patient will recieve in-app notifications when the app is open (Connected to Notification-Service using SockJS)
  
  ## Running the project
  
  The local configuration lives on the master branch but every microservice is deployed to heroku and due to changes on the configuration you can check it out at [remote-config](https://github.com/luismiguelfeijoo/diabeaten/tree/remote-config).
  
  You should open all of following links:

| Microservice |  Link        |                
| ------       | ------------ |
| Config       | [Config-Server](http://config-server-diabeaten.herokuapp.com/)            |
| Eureka       | [Eureka-Server](https://eureka-server-diabeaten.herokuapp.com/)           |   
| Edge         | [Edge-Service](http://edge-service-diabeaten.herokuapp.com/)             | 
| User         | [User-Service](https://user-service-diabeaten.herokuapp.com/)            |        
| Information  | [Information-Service](http://information-service-diabeaten.herokuapp.com/)         |        
| Glucose Bolus| [GB-Service](http://gb-service-diabeaten.herokuapp.com/)             |
| Notification | [Notification-Service](http://notification-service-diabeaten.herokuapp.com/)     |

Once opened, go to [Eureka-Server](https://eureka-server-diabeaten.herokuapp.com/) and check if all services are registered. Total list must be 5 services.
  
You must run the angular app locally using ```ng serve``` to conect to a local project or ```ng serve --prod``` to connect to the deployed microservices
  
You can create your own user but remember to use a password containing at least 6 letters (1 uppercase) 2 numbers and 1 special character of the following ($%&/@)

You may also see the admin view using 
````
username: admin@admin.com
password: admin
````
  
## TO-DO
Given just 1 week this project has come all the way over here, but the work continues!

* Spinners and loading animations
* Form validation feedback with interactive button and messages
* Add report charts
* Extend admin view
* Create a delete feature on the front (Backen Api already implemented)
* Add notification configuration
* Create an email sender with custom email templates
* Create a proper monitor registration using JWT sent by email on invitation of patient

And as always, give me your thoughts to keep adding to this awesome idea.

## Reported Bugs

* 2 hour difference on deployed microservice db
* Multiplication of notifications display on the front end
    


  
  
  
