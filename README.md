comp3021_pos_system
===================
Enhanced POS Application
(Knowledge of GUI and Simple Threads included)
1. OBJECTIVE
You are required to implement a graphical user interface (GUI) version for the point
of sale (POS) application similar to that in assignment 1. The objective of assignment
2 is to practice skills in GUI and multi-threaded programming.
2. TASKS
There are three implementation tasks in this assignment, which are the logic, GUI and
multi-thread part. The details are listed as follows.
LOGIC Part: In addition to the basic functionalities mentioned in Assignment 1, the
POS application allows a store to offer discounts to its customers. There are four
types of discounts:
1. Event discount: The store may offer an event discount on a special day. All
purchases on that day can enjoy a specified discount.
2. Customer discount: Purchases made by a customer who is a club member of the
store can enjoy a specified discount.
3. Product discount: Purchases of a product under promotion can enjoy a specified
discount.
4. Sales discount: Purchases of products may enjoy a specified discount if their total
amount (before discount) is greater than a certain value.
Let’s mark the event discount ratio as e, the customer discount ratio as c, the product
discount ratio as pi for a purchased product Pi and the sales discount ratio as s when
total payment exceeds R, then the total amount of payment is calculated by:
(1) If the total payment exceeds R
(Price1*(1-p1) + Price2*(1-p2) + Price3(1-p3)…..) * (1-c) * (1-e)*(1-s). (Pricei is
the corresponding price of the purchased product Pi.)
(2) If the total payment does not exceed R
(Price1*(1-p1) + Price2*(1-p2) + Price3(1-p3)…..) * (1-c) * (1-e).
The enhanced POS system reads various discount information from the file conf.xml.
For example, you can find the following contents in the file conf.xml under the
directory “configure”:
<discount>
<strategy type="event" value="0.05"></strategy>
<strategy type="customer" value="0.10"></strategy>
<strategy type="sales" value="0.2" value1="103.00"></strategy>
<strategy type="product" value="0.15">
<item id="ID002" value="0.15"></item>
<item id="ID005" value="0.05"></item>
<item id="ID009" value="0.25"></item>
</strategy>
</discount>
The file defines various discounts as follows:
Event discount with ratio 5%
Customer discount with ratio 10%
Sales discount with ratio 20% when total payment exceeds 103
Product discounts:
ID Name Ratio
ID002 basketball 15%
ID005 doll 5%
ID009 shoe 25%
Users of the POS system can adjust different discount ratios by changing the value
field in the XML records. For example, by adding a row with <item id=”xxx”
value=”y”></item> under the product strategy, you can add a new discount ratio
y to a specific product with id xxx ,which should be unique. (You may try different
discount values and observe the changes in the fields “discounted” and “total sum” in
the payment interface introduced below.)
The system can also display different currency symbol according to the file conf.xml.
You can see there is a line in the file.
<currency>HK</currency>
It can also be set to
<currency>US</currency>
The system will show the corresponding currency symbol in the payment window
shown below. You may also try it out in the demo program we have provided.
You can find the prices of each product in the file itemListFile.txt under directory
configure. (Please be aware that do not change the files under this directory;
otherwise you may not be able to run the demonstration correctly.) Since the prices
and the discounts are non-negative real numbers, you will need to use double or float
in your logic.
GUI Part: In this task, you should design the GUI interface of the POS application.
1). Log in
A log in window for cashier authentication will pop up when the POS starts. It should
contain two text fields for a cashier to enter username and password respectively. It
also contains a “Sign In” button for confirming the sign-in procedure. Below is one
example used in our demo program.
The system displays a sales screen after successful authentication.
Invalid input detection:
The cashier receives a failure message of “Invalid username or password!” upon
unsuccessful authentication.
2). Sales.
The cashier’s user name will be displayed at the top of the screen.
The POS system should support that the cashiers can input a sales record in two ways:
1. They can add the products to the shopping cart by directly entering the product ID
and corresponding amount; or
2. They can select the product in the product list at the left hand side, enter the amount
in the middle and click the “-->” button to add it to the shopping cart.
Cashiers can check the entered products and their prices at the shopping cart.
Cashiers can select a product item at the shopping cart, and click the “<--” button to
remove it.
If cashiers click the “Clear” button, the shopping cart will be emptied.
The cashier can check the “Is VIP?” box before pressing the “Pay” button at the
bottom of the interface if the current customer is a club member of the store. If the
box is not checked, the customer does not enjoy the customer discount.
When the shopping cart is non-empty, the cashier can press the “Pay” button (whether
the customer is a club member or not) to proceed to the payment screen.
Invalid input detection:
1. If the cashier enters a non-existing product ID XXX, a message “Prodcut XXX
does not exist” will be displayed.
2. If the shopping cart is empty but the cashier presses the ”Pay” button, a message
“Invalid sales information” will be displayed.
3). Payment:
In this screen, all items and their corresponding amounts entered at the sales screen
will be displayed in the list at the left hand side.
Detailed information of the sale is displayed, including:
Total price: the sum of the purchased products’ prices before any discount
Discounted: total discount of the sale
Total sum: the total money the customer should pay
Paid: the amount of received money
Refund: the amount of money that the cashier should return to the customer when
paid money is greater than total sum. It is obvious that when paid money is not
enough or is empty, this field should have no value.
The cashier can press the “submit” button, and return to the sales screen after
successful payment.
Invalid input detection:
If the paid money is not enough, a message “Not enough money!” will be displayed.
After a sale is submitted, corresponding purchased record will be recorded in the file
salesRecord.txt under the directory “configure” in your program execution path. The
format of the record is:
OperatorName VIP Price Discount Paid Refund
Product1 amount, Product2 amount...
Threading Part: In order to control the GUI part and the application logic part
independently, you are required to use a thread to control the GUI part and another
thread for the POS part. In addition, the POS should support the creation of multiple
Cashiers, each uses an individual GUI. You should use concurrent threads serving
these GUIs so that they can respond timely to multiple Cashiers’ inputs
simultaneously. These multiple Cashiers, however, share the same sales record file.
Therefore you should carefully program the concurrency to prevent multiple Cashiers
from writing the sales record file simultaneously. You can run the demo program to
better understand the requirements.
Testing Part Finally, please also include your test suite and test cases for unit testing
when you submit your program. You are required to obtain 100% statement coverage
to all public methods and the main routine of the POS program that you completed for
assignment 1. Statement coverage of exception handling blocks is not required.
Your test classes and test methods should strictly follow their naming regulations as
introduced in the lab session. That is:
Test class should be named as xxxTest such that xxx is the name of the tested class.
E. g: Class name: DateHelper,
Test class name: DateHelperTest.
Test method should be named as testXxx in which Xxx is the name of the tested
method. You can use a sequential number as the identifier of different test cases for
the same method.
E. g: Method name: MatchDate(),
Test method name: testMatchDate01().
It is strongly recommended that you organize all your test cases in a package
separated from your program, so that our TAs may check the code of your test cases
quickly.
When you submit your assignment, make sure that you have included a test suite
which executes all of your tests.
How to start:
(1) Download assignment2.zip package from the course website and unzip it. Use
Eclipse to import the Enhanced_POS project(click the menu File->Import, select
General/Existing Projects into Workspace, choose the folder “Enhanced_POS” you
got from the package as the root directory, and click finish)
(2) We’ve already implemented several parts for you.
a) Enhanced POS settings and product information. Packages “conf”,
”conf.currency”, ”conf.discount”, ”core.entities” are used to load
Enhanced POS settings and product information from the files in the “configure”
folder and store them in the pre-defined data structures such as the user list, product
information, currency symbol and discount strategy. You are recommended to use
these data structures in your own implementation but you are also allowed to define
your own data structures to store these information. GlobalConfiguration.java
contains the logic of the loading process and you are recommended to read and
understand it but not modify it. You can modify other files we provided by adding
more functions to simplify your implementation. Again, please use all the provided
classes if you find them useful. You are NOT required to use them. Please try to
understand them by yourself and the course will not provide special sessions to
explain them to you.
b) The process of loading configuration files. We provide you an example of how to
using GlobalConfiguration to load the Enhanced POS settings in LoadConfExample.
You can add similar code anywhere in your own implementation to load all the
configuration files to certain data structures.
(3) If you have any problems about importing the project or running the
LoadConfExample, please check your JDK version and the build path of the project
in Eclipse first. Just in case some of you still don’t know how to start, we will arrange
a lab session to demonstrate you the process and you can also ask questions if you’ve
already started.
3. GRADING
Grading will be based on functionalities. The breakdown of grades is as follows:
1. All GUI interfaces introduced above: 30%
2. Thread control part: 20%
3. Invalid input detection (refer to each interface, 2.5 points for each): 10%
4. Final result saving (in the file salsesRecord.txt): 10%
5. Computation correctness (for 4 test cases we prepared, your program can
compute correct price, discount, sum and refund, 5 points for each): 20%
6. Code quality (design and documentation): 10%
7. Good design (e.g. overall design, reusable classes, portability (some of the
classes will be used in a extended version later), clear classes design... etc.):
3%
8. Good documentation (Brief but meaningful comments for each class and
function, appropriate use of javadoc tags): 3%
9. Good coding style (e.g. appropriate indentation, meaningful variable and
method names, explanatory comments for difficult parts ... etc.): 4%
10. Test Quality : 10%
a. Good naming regulations (Strictly follow the naming regulation
introduced above. If you fail to follow test naming regulation, your score
of test quality will be under 2): 3
b. Test suite availability (You need to include a test suite containing all tests
you’ve written, and include a test runner implementation so that all tests
are executed with a single execution of that runner): 2
c. Test adequacy (You are required to obtain 100% statement coverage for
all public method and the main method of the whole program. Coverage
of exception handling codes is not required. If you fail to achieve this
requirement, your score will be based on the actual coverage level: 1 for
coverage level 0%~50%, 3 for coverage level between 51%~99%. The
coverage level will be evaluated by EclEmma report of your tests): 5
Note:
1. If your program could not be compiled, we may give you partial credits
(normally under 20% points).
2. This is an individual assignment. Please acknowledge the resources (books,
web sites and so on) that you have consulted and the people whom you have
discussed with. Please be reminded that plagiarism is a serious offence.
Students who are involved in plagiarism could fail the course assignments.
4. SUBMISSION Due date: May 17,
Assignment grading will be based on the full version.
• The assignment is due at 11:59pm on the due date and you may use Course
Assignment Submission System (CASS) to submit your assignment. To use
CASS, you may first go to https://course.cs.ust.hk/cass/submit.html. Then choose
Comp3021 for the course and select the files you would like to submit. Please
submit the file named assignment2.zip.
• Considering that it is possible you cannot reuse the implementation in assignment
1 in this assignment, please zip your assignment 1 together with the test cases that
you create as assignment1.zip and submit it as well.
• Please ensure your submitted program is self-completed, that is, can be compiled
and run by itself. For fairness, we will not supply more files to compile and run
your program during grading. And also provide us a readme file to tell us how to
run your program.
• Please submit your assignments by the due date. Late submission will not be
accepted.
• You are recommended to use JDK 6 to compile and run
your
program.
