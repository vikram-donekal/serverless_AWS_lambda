# serverless_AWS_lambda
Serverless Architecture with Java 8, AWS Lambda, and Amazon RDS(Postgresql)
This code base is an example API built with the Java 8 runtime for AWS Lambda, in the context of a common use case: an API backed by Amazon RDS(Postgresql) as its data store. In a production deployment, you would use Amazon API Gateway to proxy RESTful API requests to the Lambda functions, each of which corresponds to a single API call. When API Gateway is added, the architecture is as follows:

 ![alt text](https://github.com/vikram-donekal/serverless_AWS_lambda/blob/master/references/ServerLess_architecture.png)

By using Lambda together with Postgresql and API Gateway, there is no need to deploy or manage servers for either the application tier or database tier. If the front end consists of mobile devices and a web app statically hosted on Amazon S3, the result is a completely serverless architecture with no need to deploy or manage servers anywhere in the system, in either the front end or back end. 


EXAMPLE USE CASE:
The example use case is a Student CRUD (Create-Read-Update-Delete)operations.

To make it simple :
Student Table:
 ID;
 Name;
 Phnumber:


DEPLOYMENT NOTE:
Follow these steps to deploy the application:

Create a AWS RDS (Postgresql) table with the attributes mentioned above.

Create Lambda functions, one for each handler in the EventFunctions class.

Create a API Gateway API. Note that even if you don't do this step, you can still test the Lambda functions via the Lambda console "test function" tab.

To automate deployment of the Lambda functions and API Gateway, consider using AWS SAM (Serverless Application Model). Using SAM can simplify deploying an API, such as this one, built with a single code base that supports multiple Lambda functions. See http://docs.aws.amazon.com/lambda/latest/dg/deploying-lambda-apps.html.


INFO:
I will explain how to create AWS RDS,AWS Lambda,Aws GateWay and deploy applications.

Steps:

Creating Amazon RDS(Postgresql):
1: Create AWS Account and Login into Console.
2: Go to Services and click Amazon RDS and Create a Database of your Choice.
I have seleted Postgresql.
Go for Default values or check out Links for any advance usage of Database.

https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/CHAP_GettingStarted.CreatingConnecting.PostgreSQL.html

Go to Connectivity .Grab Endpoint and URL.

![alt text](https://github.com/vikram-donekal/serverless_AWS_lambda/blob/master/references/RDS.png)

 

If you have any connectivity issues.
Solution: Port should be open to incomming Request .Check AWS Firewall and Your system Fireswall.

Creating AWS Lamda:
Steps:
1: For my Use Case i have written Four lambda Java programs to connect to Amazon RDS and do crud Operations.

You can write your own logic.

![alt text](https://github.com/vikram-donekal/serverless_AWS_lambda/blob/master/references/handler.png)

 
2: For my Use case i used java 8 ,Maven tool to build UberJar(Fat jar)
You can develop in python,Node,java,C# as these are supported in AWS Lambda.
You can check Link: https://docs.aws.amazon.com/lambda/latest/dg/getting-started-create-function.html

3: To Build UberJar i used maven-shade-plugin
   Command: package shade:shade
   Output: CreateStudent-0.0.1-SNAPSHOT-shaded.jar

4: Once your Created Jar.Login in Aws Console Go to Lambda Function.Create Function 

Go to Author from scratch option.
Enter Basic information of Functions and create function.

5: Go to Function Code and upload Jar/Zip file you have created.

INFO: HandlerInfo is the one which AWS lambda will be calling.
Syntax : package.className::handleRequest
Example : com.aws.lambda.GetStudentById::handleRequest

6: Click save and Test by creating Test Case by your requirement.


Creating Amazon API Gateway:
Steps:
1: Create AWS Account and Login into Console.
2: Go to Services and click AWS API GateWay and Click on Create a API.

INFO: https://docs.aws.amazon.com/apigateway/latest/developerguide/how-to-create-api.html

Follow the steps and you will be ready with Serverless Architecture

For Your Reference i will be adding my JSON.

Sample: MyStudentCRUD-aws-swagger-postman in reference Folder




![alt text](https://github.com/vikram-donekal/serverless_AWS_lambda/blob/master/references/logo.png) 
