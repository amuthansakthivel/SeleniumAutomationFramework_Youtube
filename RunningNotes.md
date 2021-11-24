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


## TO DO - Add notes from here

```sh
cd dillinger
npm i
node app
```

For production environments...

```sh
npm install --production
NODE_ENV=production node app
```

## A table

| Plugin | README |
| ------ | ------ |
| Dropbox | [plugins/dropbox/README.md][PlDb] |
| GitHub | [plugins/github/README.md][PlGh] |
| Google Drive | [plugins/googledrive/README.md][PlGd] |
| OneDrive | [plugins/onedrive/README.md][PlOd] |
| Medium | [plugins/medium/README.md][PlMe] |
| Google Analytics | [plugins/googleanalytics/README.md][PlGa] |

