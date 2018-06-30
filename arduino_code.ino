#include <ServoTimer2.h>

// FSR
int fsrAnalogPin = A0;
int fsrReading;

// Conversation with bluetooth
char data = 0;

// Servo details
ServoTimer2 myservo1;
ServoTimer2 myservo2;
int angle1 = 0;
int angle2 = 0;

void setup(void) {
  Serial.begin(9600);

  myservo1.attach(10);
  myservo1.write(angle1);
  
  myservo2.attach(11);
  myservo2.write(angle2);
}
 
void loop(void) {
  fsrReading = analogRead(fsrAnalogPin);
  int num=fsrReading/5;
  Serial.print("Analog reading = ");
  Serial.println(num);
  //LEDbrightness = map(num, 0, 1023, 0, 255);

  
    if(Serial.available() > 0)      // Send data only when you receive data:
    {
      data = Serial.read();        //Read the incoming data & store into data
      Serial.print(data);          //Print Value inside data in Serial monitor
      Serial.print("\n");        
      if(data == '1' && num>10){
        Serial.print("Mobile send data as 1 after scanning barcode to open first gate......\n");   //open gate to enter product
        for(angle1=0;angle1<2000;angle1+=100){
            myservo1.write(angle1);
            delay(50);
        }
        delay(1000);
        for(angle1=2000;angle1>0;angle1-=100){
            myservo1.write(angle1);
            delay(50);
        }
      }         
      else if(data == '0'){
        Serial.print("Open second gate after payment.....");    // After Payment
        for(angle2=0;angle2<2000;angle2+=100){
            myservo2.write(angle2);
            delay(10);
        }
        while(1){
          Serial.print("BYE.....\n");
        }
        
      }
      else if(data == '2'){
        Serial.print("Open second gate for remove.....");    // On Remove
        for(angle2=0;angle2<2000;angle2+=100){
            myservo2.write(angle2);
            delay(10);
        }
      }
      else if(data == '3'){
        Serial.print("Close second gate for remove.....");    // close after remove
        for(angle2=2000;angle2>0;angle2-=100){
            myservo2.write(angle2);
            delay(10);
        }
      }
      else{
        Serial.print("NOT PROPER RESPONE...\n");
      }
    }else{
      Serial.print("NO MEssage.....");
    }

  delay(1000);
}
