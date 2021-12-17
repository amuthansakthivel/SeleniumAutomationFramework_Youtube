## Rules while working on the framework
- Write regression suite, and automation test cases in src/test/java
- In src/main/java - keep all those content that is not related to testing like - Driver Initialization, Utility classes to take screen shots, Utility classes to read and write to excel file, Classes to initialize driver, Classes for Reporting, Classes for Listening, etc
- If I have multiple tests, its always a good practice to create a testng.xml - that acts as a runner for a suite of tests
- Have the Driver information in src/main/java/ Class and have this class as a Constant class - final class, private constructor, private static final String variables
- Have setUp() and tearDown() methods
    > Have a setUp() with @BeforeMethod and have the following there
    > Driver path
    > Instantiating the Driver
    > Accessing a web page
    > Have tearDown() method and do driver closing tasks
    > driver.quit()
- Create a BaseTest class that has the common code to be referred from multiple test classes
- DO NOT open a single browser and do not run all the tests in that opened browser. If for some reason, that browser instance gets closed, all the tests will get failed.
  - Good convention - Open the browser, Perform test, Close browser
- All the tests in a particular class have to be separate
    > If test2() fails, test1() which is depending on test2() will also fail.
- If there is a Parent Child relationship between the classes, and if there iss public being used in the Parent class, change it to protected instead.
    - Always follow the order - private > protected > default > public
- It is always better to check the variable that is being coming as a paramter to a method, or any variable for that matter, before you operate on that variable, for NULL CHECK
- While working with the Map, if you access only the key, then iterate the keySet, but if you want to access both the key and the value, iterate the entrySet
- Its better to trim the string values that i am retrieving from the properties file, while i use them in the java class
. Eventhough its possible to assert in the Page classes, its mandatory to assert only in the TEST classes, NOT in the PAGE classes
- All the methods in the Page class must atleast return some type, so that method chaining can be implemented in the test classes
- Just to reuse the code, never extend from a class. There MUST be a is-a relationship between the classes to extend a class from another
    - When the sub class needs most or all of the methods in the super class, then go for Inheritance
- Use EXPLICIT wait, whenever I am clicking, selecting something, using sendKeys, else as the element is not ready, it will throw an Exception
    - Its better to wait for a few seconds for the web element to display, then do the actions on it
- Don't combine implicit-wait and explicit-wait, and AVOID implicit-wait AS MUCH AS POSSIBLE.
    - We cannot avoid using explicit wait
    - Use one proper wait across the framework
- Whenever I want others to use predefined set of values, I will use ENUM
- Its always good practice to attach the exception log in the EXTENT REPORT when a test case fails, so that I can use these in future to fix the issue, instead of checking these in Jenkins logs
- If there is NO inheritance relationship between two classes, and if I have to use one of these classe's method in another class, instead of extending
without having a IS-A relation between these classes for using the method, follow as below,
    - make the class that is holding this method as final-so no one can instantiate it,
    - make this method static, so that I can use this method using the class name
##### Data Provider
- It is good to have 1 dataprovider method that can handle all the test methods
- Data Provider will be called by each and every test method, so make sure we are not interacting with external files - excel, json, yaml, etc EVERYTIME a test method calls the data provider method
- It's better if we read it ONLY ONCE from the corresponding file, STORE it in a collection, USE it
- Do not make the Excel and Data Provider tightly coupled, the framework can handle other files like json, yaml, etc instead of excel
- When reading integer, decimal from excel cell, add single quote to the cell to convert it to string
- When an excel cell is empty, add single quote to make it empty string, else, whiel reading from excel, we will get NPE
- Its better NOT to use RetryListener in the Selenium Framework. Instead of making the failed test case run once again and making it pass, fix the failed test, and let the test fail with valid defect details
- If I have many similar web elements, IT IS NOT SUGGESTEDTO USE PAGE FACTORY, as it increases more code
    - If I forget to do constructor initialization, I will get NPE

## Driver Class Rules
- All the test classes should be final, and have a private constructors, so that others cannot extend from this classs and also will not be able to create object for the classs.Restrict as much as possible for others to create an object for the class that I create
- Do null and not null checks for the Driver class implementation. Even if there are wrong implementation in the code, to invoke multiple driver instances, we will avoid it using these null and not null checks
- Do the same null and not null checks using Objects class methods

## Running tests in parallel
#### Java concepts
- local variables are thread-safe
- a primitive local variable is stored in the stack
- w.r.t a referenced variable, the variable is stored inside the stack, but the reference is stored in the heap memory, and using the hashcode, the reference will be found
- which ever object that's there in the heap memory, any thread can operate
##### Problem occured:
- If a variable is static, then that is common for all the objects. Here we have two chrome driver objects, and both of them are being accessed using a static class variable which is causing the issue - StaleElementException, while we are running the applicaiton using multiple threads

##### Solution:
- Using the ThreadLocal class implementation, only the thread that is setting the value to a variable, can get that value
- A good way to protect static class variables using multiple threads

## Static Blocks
- Use static block to initialize the static variables, and to do that ONLY ONCE, so that the static class variable is available in the memory for every one to use
- Usage of static block like the below, is an Eager Initialization
- A static block will be executed when the Classs Loader loads the class files into the Memory, which is before my actual code is getting executed, before the main() method
- Make sure to create a static block ONLY if you are actually using the variable, else the memory will be wasted
- I can have multiple static blocks
- What is the problem with the Properties File approach? - Above - ReadPropertyFile class is nothing but my class, where i am loading the property file in this class and getting the values
##### Problem to AVOID
- We are making a conneciton to Property File using FileInputStream, loading the properties file, getting the valuess from the properties file, EVERYTIME, which I can AVOID by moving the same to a static block
- Also, the Property class that I use to load and get the property file values, is like a HashTable which is little slow but thread safe
IMP - If I am using the values of the properties file, using the Property class that implements a Hash Table, it is better to convert the Property object to a HashMap
- When using the Properties file, we are doing the activity like below, only when we need it, which is Lazy Initialization
- When the assertion fails, this statement gets displayed

## AssertJ library, Soft Assertions, BDDAssertions
- AssertJ - String
- AssertJ - Int
- AssertJ - Map
- AssertJ - List
- Hard Assertions
    - Whenever the test fails, the test stops there
    - In the below case, the below statements will not get executed, if the assertion fails
- Soft Assertions
    - Everything is similar to Hard Assertions, but only if I call .assertAll(), then only the library will assert all the listed assertions
    - To work with Soft Assertions, use the SoftAssertions class
    - Whenever if it does not matter if the validation is pass or fail, I should still proceed with the test, then go for soft assertions
- BDD Assertions
- JodaTime Assertions
- Asserting a custom class


## Page Layer Creation - Page Object Design Pattern
- This pattern is a Structural Pattern, as it helps me to structure the pages
- Using this pattern, I will move all the things not related to the tests, to the page layers
- All the page specific locators, methods are encapsulated in a single class
- In the framework, create the PAGE class in src/main/java under pages package, create the TEST class as usual in the src/test/java
##### Method Chaning:
- We can write optimized code using Method Chaining
- Method chaining can be obtained through one way as below, by returning the Anonymous object,
so that I can invoke methods in the RemoteWebDriverOptions class using the manage() method
- When I am returning the same class object, instead of doing like below, use "this"
- So, replace the above code as below

## Page Factory
- Lazy Evaluation means, to find the Web Element only when it is needed, Other wise Selenium will only initilize the Web Element, but not use it
- Normally people say PageFactory have to be integrated with PageObjectModel, for the framework to be effective
- Normally people say PageFactory have to be integrated with PageObjectModel, for the framework to be effective
- When comparing with the advantages of the Page Factory, there are so many disadvantages like
    - We will get StaleElementReferenceException, when I am working with latest ajax application frameworks like Angular, React, etc
    - Using PageFactory can only save few minutes, in a regression suite run for a few hours, and as I am getting saved with a few minutes,
    - I cannot risk getting new Exceptions like StaleElementReferenceException, NullPointerException, etc and it will also consume my time in debugging to know the root cause of the issue.
    - Also, I can save the same amount of time using other concepts like ThreadLocal, Docker, Headless browser implementation, etc.

## When to use Inheritance - Creating reusable methods
- Move all the most frequently used actions like - click, sendKeys, etc to the Base class as these methods will be used across all the page layers
- Sub classes should ONLY have access to call these reusable methods, not the test classes. So use protected access modifier instead of public
- Just to reuse the code, never extend from a class

## ENUM
- For ENUM, Java compiler by default ADD toString() implementation
- When I declare my type[Eg: COLOR Enum Type] as Enum, it will extend the java.lang.Enum, thats why I cannot extend from another class,as multiple inheritance is not possible in Java
- An Enum constructor can only be private, not public

## Data Provider - Helps me to run same test case with multiple sets of data
- Data provider works in this way(whatever the test data might be - excel/json/yaml/etc)
    1. READ the value from the test data source 
    2. Put that value in the Object[] array
    3. Feed that value to data provider 4. Then, dataprovider will take care of feeding it to my test method/methods
- Why I need a data provider, if I can run a for loop to execute the test with multiple sets of data
- problem 1: Even if i am iterating for 10 times (say I am iterating the for loop for 10 times), the test count will always be 1
- problem 2: If there is some failure in the first iteration, we do not know the remaining iteration results
This is a Defect Masking scenario, where one defect is masking other defects
- DataProvider solves the above two issues
- Tell the test method what the DataProvider is, and also if I do not give any name to the DataProvider, the dataprovider method name will be the DataProvider name
- It is not mandatory to return an 2D object array - Object[][] array using DataProvider
- When using the 2 dimensional object array, the below is the THUMB RULE
- If I am returning different values from the dataprovider method, then use Object[][]
- If every value is of same type, then use that particular type as return type for dataprovider method
- If the @DataProvider data provider method is in the same class, then the @Test method does not need any further information other than the name of the data provider method as below
- The @Test method first tries to search for the data provider in the same class, if not TestNG will search in its parent class, if it is not available there too, then the @Test method throws the exception
- If the @DataProvider method is in a different class and the test method is in a different class, then we need to provide that information to the test method
- There must be a default/no-argument constructor or you must have the @DataProvider method as a static method in the Employee class, else we will get exception
- Using the same data provider for multiple test methods
- I have to implement Reflection concept - that will help me to find object properties at the runtime, instead of the compile time
- Normal scenario
    - I will read data from the Excel and feed the data to data provider
    - Dataprovider will feed the data to the test method
- If there are too many columns in the excel, which I will be supplying to the test method,
instead of having too many parameters, use a HashMap as a parameter

## Data Provider with JSON - when i have my test data in the form of json/yaml/etc
- To convert json to java type, use jackson libraries

## Including Data Provider in the framework
- To run >=1 data sets using the data provider,in parallel, use as below
- To control the number of threads that the data provider can feed in parallel, configure as below

## TestNG Listeners - most frequently used listeners
- If both Test class and Lister class have similar methods for a similar purpose 
    - like @BeforeMethod = onTestStart(), @AfterSuite = onFinish, etc

- REASON: If I do the setup and teardown activities in my tests, my tests are tightly coupled with these activities which I have to avoid to keep only that information regarding my tests in the test classes.

- Instead have all these setup and teardown activities in my listener
- By default, NONE of the implementatio of Listener class executes when I run the test. To make that happen, do the below two approaches
1. Add the annotation to the Test class - NOT SUGGESTED
2. Have the below configuration - FOLLOW THIS
    - To run the first test case among my list of test cases, I will have the below configuration
    - In the Listener - MethodInterceptor class, have the below configuration
    - In the testng.xml, have the below configuration
    - Now, if I have 10000 test cases, the above is not the proper approach, rather, do as below. Have the test case data fed through excel sheet through data provider, to the method interceptor listener, and which ever test cases having the option of Execute as YES, will only execute
    
- I can customize the test case properties as below, so that they can run accordingly at runtime

##### IMethodInterceptor LISTENER 
    
- To customize the test case execution at RUNTIME - run few, don't run few, etc
- This listener tells,
    > I will give you whatever the methods that I am going to execute, and you customize the methods in this[like adding more methods, removing few methods from the execution, etc]

    > But at the end, give me the list of the methods that I want to execute

## AnnotationTransformer LISTENER 

- To control the annotations for the referred test method
- MethodInterceptor Listener(the intercept() method) will execute ONLY ONCE for the entire test suite
- But, AnnotationTransform Listener(the transform() method) will be called each time the test method gets called

## RetryAnalyzer LISTENER 

- To control the annotations for the referred test method
- Whenever a test is failed, the retry() method will be called. If this retry() method returns TRUE, the failed test will be re-run

##### Is rerunning failed tests a good practice?

- Its always best to fix the failing tests instead of rerunning the failed tests by either of the below option
    1. Using RetryListener
    2. Rerunning the failed tests listed in testng-failed.xml

## Implementing Extent Reports - using Listeners
- Create a class[src/main/java/my base package/reports/] that has the set up, tear down and test methods content for Extent Reports implementation
- Call the Extent Report methods - init and teardown from the BaseTest
- Create a class by using ThreadLocal to avoid thread safety issues
- Now access these methods in my test, to display reports after running the test
- Now, any team member/manual tester can access all these methods, which i have to restrict, and allow to have access to pass() fail() skip() methods
- I will achieve this by the below two ways
    1. creating a class - ExtentLogger and calling these methods there,
    2. and also restricting public access to the methods

- Thereby, anyone can now use only the three methods, and I restricted the access to all the available methods
- Create a listener class that have the information about the ExtentReports, instead of having the seup(), teardown(), etc in the BaseTest, have this in the Listener
##### REASON 
- Test to be free from anyother thing other than testing. All other stuff have to be in their respective classes
- To avoid TIGHT COUPLING between the Test implementation and ExtentReports implementation
- Tell TestNG, that there is a Listener class that is listening all its Events
- Until now, whenever I run a new test, it will override the old report, but to have a dynamic report that has its own timestamp, follow as below
- Also give control to PO, Manual testers to have the screenshots for failed, passed, skipped tests, to operate from the Properties file 

## Disabling TestCase at Runtime using MethodInterceptor Listener
- Use MethodInterceptor Listener to get the control at RUNTIME to disable certain test cases

## Integrating Data Provider with Excel Sheet
- Objective is to create a test data sheet in the excel document, and feed this data to the runner sheet and to get this data in Object[] and provide it to Test method
- Our objective is to replace the below hard code values with the values coming from excel sheet. Again, this can be from excel sheet, JSON, YAML, etc
    - Why it is NOT Good?
    - Because it is defined in one test and NOT COMMON ACROSS all the different test classes

##### How to know which test case is calling this data provider?
- use refection concept as below
- Instead of having the data provider like above as hardcoded, use DataProviderUtils class and have the logic to customize the data got from excel, using ExcelUtils
- Use this data provider method name in my test classes, and let the tests know where this data provider is
- Already ExcelUtils class have information on fetching information from Excel

## Retry Failed Tests - IRetryAnalyzer, AnnotationTransformer Listeners
- Create a class implementing IRetryAnalyzer, and add retry logic to retry() method
- Show Test method on where the created RetryListener class is
- If a test case is failed, and retried once again, and even then if it fails, TestNG makes this test case as skipped
- When there are many test methods that have the same properties like - dataProvider, dataProviderClass, retryAnalyzer, etc as below,
instead of repeating these for every test method, lets have these in the AnnotationTransformer listener class
- Create a class AnnotationTransformerListener
- Add this listener in testng.xml
- Now, remove the repetetive annotation properties, as these will be set at runtime by the AnnotationTransformerListener class


## Create Custom Annotations, Integrate with Framework

- Try to log list of users who worked on the test case in the Extent Report, so that I can ask them to work on a defect
- As so much content is being hardcoded above, use Annotations to recfactor this usecase
- Annotations do not have any direct impact to the code. To use annotations in the code, I have to use Reflections concept
- RetentionPolicy= RUNTIME, means, I can access the content using Reflections
- Create a annotation type
- Instead of the above code using Extent Manager, now use the Annotation
- Next step will be, how to add these details to Report using Reflections
Create these methods in ExtentReport class
- Next step is to call these methods from the ExtentReport Listener

## Need for Enum[] to be used with custom annotations
- Create an Enum
- Update the custom annotation with the Enum
- Update the existing method to retrieve details from Enum type

## Try with Resources concept
- We use the Try With Resources concept, to close the streams automatically [ No need to write finally block ]
- We can use try with resources only if your class implements AutoCloseable interface , Ex: FileInputStream
- I can have mutiple streams to be used within try with resources


## Exception Handling
- All a developer can handle will come under the category of Checked and Unchecked exceptions
- UnChecked/Runtime Exceptions - Java cannot interpret these, and Java donot ask me to handle these.
    - But as a programmer, if i know a RunTimeException can occur, then I can handle using try catch block
- Checked/Compile Time Exceptions - Java can interpret at compile time
- Throwing Exceptions too will be a kind of Exception Handling, but, in case of any exception scenario, the program gets terminated as I did not handled
- If I do not want to terminate the program in a exception scenario, I will have to handle the exception using try catch block
    - In this way, I do not have to propagate the exception to the caller methods
### Issues with this approach
- If the bottom level layers handles the exception themselves(here b() and c() methods), then the top layer is unaware of these bottom level exception
handling
- Its better to entry the exception in each of the layer and handle the exception at the top layer
- I can also throw the RunTimeException, and compiler
    - will not ask me to handle this
    - will think that the program is getting terminated in this case
- With the above approach, by throwing a new RunTimeException("exception message"), I will stop the program from now continuing further and
to not give raise to other irrelevant exceptions like NPE, etc
- But instead of RunTimeException, create another customized exception. Why?
    - The exception itself has to tell me what this exception is all about

### Conversion of one exception to another - Wrapping of Exception
- Example:
If I get a DB exception like - Table Details are not available, or Query did not fetched any value, or any error that is not relevant for an end user,
in that case, i can convert this exception/wrap this exception into a customized exception to show relevant information to the user, saying "connectivity error, please try again later"
- I can also throw a new RunTime exception
IMPORTANT:
If I throw a new exception, the exception's stack trace WILL NOT be propagated to the caller methods

### Exception Consolidation
- Instead of throwing exceptions then and there, I can consolidate all the exceptions and throw a RunTimeException

### Creating a custom exception
- Create a customized exception that extends from RunTimeException,because the program will be terminated once the exception arises.
Example - If the data provider excel sheet is not found, I will have to terminate the program there itself
- As my custom exception is extending RunTimeException, i do not have to throw the Exception like below, and I can remove the "throws Exception"
- As I don't have to throw this Exception anymore, I can go to the methods where this getValue() is being called, and I can remove the
"throws Exception" there as well

#### Why I have to STOP the program in case of RunTimeException?
If there is a problem, I need to STOP the program. There is no point in catching them and re running the program to get another error message

### Handling Exception in static block
- Instead of catching the exception in the static block, which arises chance to another exceptions, as below, throw an instance of RunTimeException
- Static Block will be executed even before the main method, as this block executes when the class loader loads the class into memory, and there
is no JVM available for me to handle the execption raised in the static block
- To solve the above issue, use System.exit(0); in the catch block

#### Exception Handling Summary
- Compiler can detect the checked exceptions at compile time so that i will have to either
    - handle it using the try{}catch(){} block, or
    - propagate the exception to the caller method
- When to use try catch?
    - When you think this operation is very small operation and you don't want to terminate or interrupt the user flow, then use th try catch block
- If you want to stop the program?
    - Either i can throw the exception to the caller
    - If you do not want to throw the exception to the caller, as there are so many caller methods, and thereby you don't want to propagate the exception
    then, you can try, catch and rethrow in the form of runtime exception

## Java Documentation for framework
- To overcome the below issues,
- Different OS
- Different browser versions needs different driver versions
- To solve the above problem, this guy have created a webdrivermanager .jar
- So, instead of writing lengthy code just for locating the driver, you can leverage boniagarcia's drivermanager's .jar utility
- From the second time onwards, this utility will search for dricver information from cache, and the result will be fast
- It uses this resolution.properties to find the resolutuon between the browser and driver
- The resolution cache(TTL - Time To Live) will wait and will not work until the cache expires
- Incase of any issues in wedriver, invoke .clearResolutionCache() method, and WebDriverManager newly fetches the Driver informtIon
- If I am behind the corporate network proxies, and if i am getting exceptions while using WebDriverMagaer,
follow as below to work with WebDriverManager

## Problems with Test Automation, What Docker Solves?
- Why I have to learn Docker, what are the problems that I have
- The below are some of the common problems that I face in Test Automation

## Virtualization vs Containerization
- Container will use the HOST OS
- Containers will only need few binaries and libs, other than these, the container will consume all other things from the host os
- Therby spinning up and spinning down is very faster

## Creating Driver Factory
- Create a DriverFactory class to avoid clutter in the Driver classs
- Keep the Driver class clean like the below

## Docker Basics
- Containers are light weight VMs. In this VM, I can have only one application
- Docker Engine works similar to Client Server architecture.
- Server:
- Once I start the Docker, it will start as a Docker daemon, which will continuosly run until i shutdown the PC
- Docker daemon is responsible for managing my containers
- We dont have access to me to work wit, rather i can interact with it using Docker Client CLI
- Client:
- I will be sending the commands through the command prompt, and these commands will be sent to docker daemon through REST API requests
- - If I have to create a container, I need an image that needs to be installed in a particular VM
- I will do : docker run <image_name>, which will create a container for me
- container is an instance of the image
- Docker Hub will contain all the images for me to pull(means to download)
- In Test Automation world, all the containers that I use will be mostly of linux based containers
- Means, Google team has developed and uploaded the chrome image based on linux, to docker hub
- So whatever the containers that I create from the chrome image will be of linux containers
- I can have multiple containers from the same image
eg: I can have multiple tabs in the chrome browser
- I cannot have multiple images inside a container, as the container is the running instance of the image

- Above uses cases for automation test engineers can be understood using below example

### SCENARIO - A
- Inside Docker, I will have Chrome, Firefox and Selenium Grid
containers - image like Chrome browser and Firefox browser
- I have my automation tests in my physical infrastructure
- Instead of running the tests in my local machines, I am delegating
the tests to the containers in docker
So the tests will be ran and the test results will be uploaded to the
local infrastructure

### SCENARIO - B
- I can ship all my automated tests in the form of an image
and I can create a container for that image, so the entire entity will be
dockerised now

## Docker Image
- Tags are specific version of the image, eg: chrome 88, chrome 89, etc
- To pull an image, follow as below. By default tag will be latest, if you want to pull a specific tag, append the tag name
- All the official Docker images will have author name as library

### Key Points
- A particular image will have all the things necessary to run the chrome image
It will not depend on anyother thing else
- Class / Image
Object / Docker Container
- I can create multiple containers from the same image

## Executing tests in - Selenium Standalone Chrome Container
- Docker commonly used commands
- I can only pull an image, I CANNOT pull a container
- PULL the selenium standalone-chrome IMAGE. Here, Selenium is the AUTHOR, and sandalone-chrome is the IMAGE
- Now create a CONTAINER for the IMAGE that was pulled above
- Now the container has been SPINNED UP, and Docker has created an ID for the created container
This is similar to initializing an object, we have not used it until now. To use it, I have to start the container
- To start the container, use as below. With the below information, the Docker DAEMON will start the new container
Whenever docker responds back with the container id, then I can understand that the container is started
- To check whether a container is created and started, follow as below
- To stop and remove the container, use as below
- Instead of creating the container and starting it, just use as below, and the command prompt will go in the listening mode

- Here, i am doing a port mapping my locall port to container using(-p), then following the image name(from which image i want to create a container from)
- i can refer to the below information once i starting the container
- i have to use -d to have this container start happening in the background, thereby not allowing the container to go in the listening mode
- i have to use --name <my-container-name> to name my container instead of referring it with the automatically generated id
- i have to use -p to do the port mapping of the docker container running in my localhost to the docker container at remote

- When accessedt the container at the particular port
- The Selenium Standalone server is running in docker, and i am seeing this in my local, as i did the port mapping

## Executing tests in - Selenium Standalone Firefox Container, Problem with using STANDALONE CONTAINERS
- To create a docker container from the firefox image from a tag - 85.0, do this
- Now, both the firefox and chrome containers are running at 4445 and 4444 ports
- Now selenium-standalone-firefox can be accessed at 4445
- Now I can run both the tests in the standalone selenium firefox and chrome containers
- Now the test runs in standalone firefox container too

### What is the problem here?
- For each and and every browser type, the port gets changed, and it will be very difficult in the case of running many tests, it will be tougher to delegate tests to docker

### Solution?
- To use hub, that will run at a port, and i will connect with hub, and hub will automatically delegate the tests to corresponding browsers at different ports


## Getting inside of a Standalone Selenium Chrome Container

- Lets access the shell in the container that i am using now
- By default, the container will not give permission to access, to have the access use as below
- docker exec
-t(to tell i want to see the output from the command prompt)
-i(i want to interact with the command prompt)
- navigate to location - /opt/selenium
- With the help of the above three , i could able to execute my tests in this container
- Selenium Standalone Chrome is a container that will contain its own Selenium Grid kind of infrastructure, and it has a hub, it has a node
- If I am executing my tests on a docker container, means, i am executing my tests in a linux machine


## Setting up Selenium Grid in Docker Environment
- Normally companies spend so much of money in setting up Selenium Grid infrastructure, but with the advent of Docker, this processs has become so easy

### Problem 
- If Chrome and Firefox images are running at 4444 ports in Docker environment, I will have to add two different ports in my tests, and this keeps on increasing and time consuming

### Conclusion:
- If I have to execute my tests in a single version of chrome/firefox, then use Selenium Standalone Chrome/Firefox
If my requirement is to execute my test on multiple browsers, these standalone containers will not serve the purpose
- Solution is to use Selenium Grid, and if i move this to Docker, it will be Dockerised Selenium Grid

- Selenium team has created the BASE IMAGE as Linux, so all my NODES and HUB are maintained in Linux containers
- Steps to move the Selenium Grid to Dockerised Selenium Grid
1. Create Selenium Hub Container
2. Create Chrome Node Container and link it to Hub Container
3. Create Firefox Node Container
 

1. Create Selenium Hub Container
2. Create Chrome Node Container and link it to Hub Container
- If you already pulled the images, search for those images as below, and refer to them
- Now as i already have these images, I will have to link these images to the hub
- Read the above command like:
docker run -d --link <the name of the selenium hub to link with>:<it will act as the hub> <what is the image that i want to link to this hub>

3. Create Firefox Node Container - Similar way
- List of docker containers until now
- Now execute the test

## RESTART Policy - Its importance in Test Automation
- I am solving the below problem with this Restart Policy
- RESTART POLICY: Whenever my container is getting crashed, using this, the Docker daemon will SPIN UP the SAME container for me with the SAME IMAGE and SAME INSTRUCTIONS PREVIOUSLY USED
- To Restart Flag = Restart type
- use update if the container is already in running state

## Why is a Restart Policy important?
- Eg: In my automation regression test suite, there are 500 test cases, and if the 5th test case got failed due to the volume or space constaint issues,
the remaining 495 will not be executed. To fix this, employ Restart Policy
- There are 4 Restart Policies
- NO - By default all the containers have NO as the restart policy,
ON-FAILURE - Restart only on an abnormal failure, but not on the Docker Stop and etc,
ALWAYS - To restart the container all the times,
UNLESS STOPPED - The container will gets restarted all the time, except when the docker daemon is stopped, or except i manually stop it
- In my existing scenario,
- I can choose ALWAYS restart policy to Selenium Hub docker container and
- ON-FAILURE restart policy to the chrome and firefox containers
And also, mention how many times the container have to be restarted, and even then if it is failing, report me back with the issues
- current scenario - working with busybox container
- setting restart policy as on-failure and told it - Whenever there is a FAILURE, RETRY for 3 times
- To check if the restart policy is updated in the container or not, follow as below


## Use update if the container is already in running state
- I can either STOP the currently running containers, and while creating again, use the restart policy
OR
- use docker update --restart <flag> $(docker ps -q)
- To check if the restart policy is updated in the container or not, follow as below

#### If I am using a docker-compose file, or if i am using the dockerised selenium grid, employing restart policy is important in it

#### I will go to docker-compose file, if i have to link multiple chrome and firefox containers to the hub, as it is manual and time consuming

- The reason of me using the Docker Compose file is, instead of me running all the below commands manually, the docker-compose file, do those for me
- And stopping and removing of the containers is also manual and time consuming

### docker-compose file - file formatting is IMPORTANT and ERROR PRONE

- This is the latest version of the docker compose file
- The containers in the docker world are termed as services[service = container+ all its configuration]
- service can be considered as a wrapper around the container, service and container are little bit
different, but not completely the same
<== as i can have multiple values for ports, i have written differently with "-" in the next line
volumes:
- docker will mount a very small amount of space, it is not sufficient for running lengthier tests
- -v /dev/shm:/dev/shm is a known work around to avoid browser crashing inside docker container
- in the above way, i am sharing the memory from localhost docker to where the docker container
- depends_on:
- if i don't use depends_on in docker compose file, docker will kick start all the services in parallel
- here we are telling, please wait until the selenium-hub service is ready, after that trigger chrome, etc
- Because, I have mentioned depends_on: selenium-hub, chrome and firefox are waiting, until selenium-hub service is running
- To see the list of containers those were kick started using docker-compose file,
- To scale the containers, use as below
Eg: If I give scale value as 10 9 will be added, as one is already up

## Running Selenium Tests in different browser versions using Docker - Changes in Framework
- Usecase: I want the tests to run on the specified browser versions in the excel sheet
- Add new column to add the browser version
- Now the data provided in the excel sheet will be available to me in the form of dataprovider
- Values in the excel sheet are available in the form of map
- This map will be feeded to the test method in the form of Object[]
- To get the value in the excel sheet, i am converting that Object[] into HashMap, and from the HashMap, I am getting the values
- Add a new parameter to the method
- And in Driver class, i am passing the browser and calling DriverFactory, which is responsible for initiating the drivers
- Add a new parameter to this method, to fetch the version
- Add a paramter to the DriverFactory method too
- Add the capability to set the version, for both the chrome and firefox

## Summary until now, and the NEED of the real time dashboard

##### ELK Stack - Its inclusion in test automation
- Elastic Search:
- Is real-time distributed analytics engine - means, If I insert the data into elastic search, the data will be indexed immediately and i can access those documents immediately
- The indexed data can be accessed from Kibana
- Elastic Search internally uses Apache Lucene, which will search the more relevant documents in the order based on the search query
- Elastic Search exposes REST API

##### Run Elastic Search, Kibana in Docker Containers

##### How to work with Elastic Search

- If I am only inserting the data, there is not need to give the id for the document.
In this case, I am only inserting the data and using Kibana to get the data, so no need to give the id
- If I want to retrieve the data, then give the id
- To do the normal table operations like select *, etc, in the Elastic Search, use the Elastic Search Head Plugin in Chrome
- Apache Lucene works underneath, and will give a score to the query


#### Creation of Real Time Dashboard for Test Automation using Kibana
- Instead of using Elastic Search Head Plugin, which isn't visually appealing, lets use Kibana to display dashboards
- Elastic knows by Index, Kibana knows by Index Pattern
- I have to link both the index and the index pattern
Step 1: Go to Stack Management - Index Patterns / Create Index Pattern
Step 2: Select Discover from Analytics and select the elastic search index
Step 3: Select Visualizations and create various visualizations
Step 4: Add the visualizations to the dashboard

## Integrate Selenium Automation Framework with Dockerized ELK using Java
- Add the RestAssured dependency
- Create a class that invokes the elastic search's POST endpoint

- Integrate this standalone file within the framework
- Create the utils class, and include the above code there
- As the test-name and test-status are dynamic, send them as parameters to this method
- I will be supplying test-name and test-status from the listener class dynamically
- Call this util class method from the listener class, when ever a test is getting passed, failed, skipped
- Have the changes in the excel sheet, to have the tests run, skip and fail
- Do the changes w.r.t docker, as the run mode is remote, start all the docker containers - chrome, firefox, hub
using docker compose file configuration - docker-compose up
- Run the test suite using testng.xml
- With the below configuration, I can avoid sending the results to ELK stack
- Usecase: When I am developing the application, and work is in progress, i can stop sending the data to ELK
 

## Portainer - Run, Manage Docker containers without using commands
- Portainer is a lightweight management ui that helps me in managing my various docker environments - local/remote/cloud environments
- Portainer is also available as a docker container, so pull the image and run the container
- The below is the content to update in docker-compose file w.r.t elastic search and kibana
- To start portainer docker container, use as below
- To know what are the containers those are in running state - i will use docker ps
- But the same can be seen in a presentable way in the portainer


- I can do the following things using the Portainer
- Spin up, down a container
- Check the available images
- Build a new image
- To create a new container, follow below
- Click Add Container

- Once the container is started, check the logs



## Refactor existing code
- Always have a TODO place holder for any refactoring that i plan to do later, along with completeling the existing fucntionality
- Even though i use the remove(), people will still invoke the setDriver() and pass a null reference, in this case, do the null check


#### Selenoid - Live Preview and Video Recording Capability for tests running in Docker
- Until now, I have used docker containers to run my tests in remote. But there is no facility to see the tests those get executed as a video recording
- To have that, I will have to download and use VNC viewer, thereby I cannot attach this into JIRA, etc
- To run the aerokibe selenoid, use as below
- Start another docker container - Selenoid UI
- Run the Selenoid through a java program
- Once we run this program, we can see the live execution in Selenoid

#### Using Consumer Interface in the Framework
- Our intention is to allow others to use only the static methods, and hide all the other ExtentTest library methods
- UseCase: When I want to log the information not only in the Extent Reports, but also in the Jenkins Console
- Using of Log4J will add information to the existing class's code.
- But the good coding practice says, we should not ALTER the EXISTING methods, meaning,
"We should not modify the existing class, but we have to provide a facility to extend the class"
- Create a class FrameworkLogger and have this consumer interface implementation
- This Java 8 Consumer interface implementation is a optimal alternative to ExtentLogger class



## Miscellaneous Notes - Forming an XPath, Dynamic Elements, Explicit Wait

- I have to read the below XPath as
- It's a <div> tag with the text value = Echo & Alexa, and as I prefer to click on a Anchor <a> tag rather than clicking a <div> tag,
give its parent tag as well as - //div[text()='Echo & Alexa']/parent::a
- Implementation of Dynamic Elements which is only possible in normal approach and is not possible using Page Factory
- using this Dynamic Elements approach, I can construct my Web Element [here - xpath] DYNAMICALLY at RUNTIME,
with this I don't have to create 10 new elements, rather I can do the job with only one line with the Dynamic Web Element Locator strategy
- In the above case the scenario is, if only the text of the xpath changes, but all other xpath expression remains the same, I can pass the changing value at runtime rather than constructing the web element everytime
- Not Possible using Page Factory

##### Explicit Wait
- If the selenium framework finds the element before than the mentioned seconds - 10 seconds above, then the control goes to next step
