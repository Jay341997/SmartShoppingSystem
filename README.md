# SMART SHOPPING SYSTEM

## How it works ?

The aim of this project is to develop a smart shopping cart using arduino and mobile application for smart shopping. In this smart shopping cart, each cart have their mobile phone as barcode scanner and products have barcode on it. This shopping cart is locked. And it opens when user scans any product by mobile phone placed above cart, then cart will open for a while to put that product into the cart. To remove any product we will have option of checkbox again that product in bill, so customer will tick all products he wanted to remove and then he will go to security guard near them, now security guard will enter password to the mobile phone and all items which were ticked were removed from bill and guard will take out that ticked items. Product will be added or removed automatically from bill which is going to be displayed on customer’s mobile phone. Bill will be generated on mobile application. And after finishing shopping user can pay the bill online or by cash at counter.
Initially we know weight of shopping cart. At the end we will check the
weight of loaded cart. And we will get weight of all products put inside cart and
cross check it with mobile bill.

Components:-
Arduino Uno
HC-05 Bluetooth Module
2 Servo Motor
Jumper Wires
BreadBoard
FSR Sensor
Mobile phone application 
Design of Working Model…
Circuit Diagram:
 

1.) Bluetooth HC-05:
	This shopping cart have arduino, HC-05 bluetooth module and 2 servo motors on it. Initially, customer have to connect his mobile app with bluetooth module on cart using bluetooth connection. We configure bluetooth module as slave and set password 1234 on it. To configure bluetooth module we need to enter in AT command mode using circuit and code given below.

 
 
 

2.)  Servo Motors:
In smart shopping cart we used two servo motors for two doors. 
	First door is above the cart. This door is also equipped with FSR sensor to detect whether there is any product is on that or not. When customer put any product on that horizontal door, FSR sensor will give readings and when customer scan that product’s barcode from scanner it will send signal to arduino to open that door and put that product inside cart. This door opening and closing is too fast so that there is no chance for cheating.
	Second door is vertically situated on the side of cart. This door will open when customer wants to remove something from cart that he does not want, so customer click on remove button on mobile application, it will ask password on next step. Security guard have that password. Now security guard enters password second door will open and guard will take out things that customer want to remove.
	When customer pays his bill second door will automatically open on the trigger of mobile application connected via bluetooth. And customer will take out everything from cart.
 


3.) FSR Sensor:
	FSR sensor is used to measure force on that sensor. We are using FSR sensor on the first door of cart. When there is product placed on that sensor it will gives higher force value in arduino and when arduino gets signal from mobile app for successful scan it will drop that product into cart.
 


4.) Mobile Application
	Mobile application of smart shopping system. Our mobile application can check the availability of that product at that shopping center. Given below picture is screenshot of first page of our mobile application where user can check availability of product at shopping center without going there.
	Start shopping button will be disable until customer connects his mobile phone with smart cart using bluetooth connection.

  

	When Customer will connect his mobile phone with smart cart via bluetooth he will enter the main billing page where there are 2 buttons available “REMOVE” and “BUY”. This page will show all the products that customer put inside the smart cart. It will show total amount and total weight at bottom.
	At the end when customer clicks on “BUY” button in payment area, one of the guard will check the weight of cart and cross check it with mobile phone bill. Then customer will pay the bill using online methods and door will open to take out products from that cart.



  

	If customer wants to remove product from bill, he will tick that items on mobile phone and went to guard. When guard removes everything ticked he will enter the password and that products will be deducted from the bill.



Problem Solved:
?	Customer do not need to wait in long queues of cash counter.
?	Customer can pay money by online payment methods like paytm, netbanking, debit card, credit card etc without waiting on counters. Customer can also pay by cash on counter by showing bill that was created in customer’s mobile app.
?	Customer is able to check availability of the products that he wants without going to shopping center with the smart mobile application.
?	This system saves lots of time of customers.


