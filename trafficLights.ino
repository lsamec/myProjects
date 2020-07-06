
const int red = 11;
const int yellow = 7;
const int green = 4;
int state = 0;

void setup() {
  // put your setup code here, to run once:
  pinMode(red, OUTPUT);
  pinMode(yellow, OUTPUT);
  pinMode(green, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  
  if (state == 0){
    digitalWrite(red,HIGH);
    digitalWrite(yellow,LOW);
    digitalWrite(green,LOW);
    delay(3000);
  }
  
  if (state == 1){
    digitalWrite(red,HIGH);
    digitalWrite(yellow,HIGH);
    digitalWrite(green,LOW);
    delay(500);
  }

  if (state == 2){
    digitalWrite(red,LOW);
    digitalWrite(yellow,LOW);
    digitalWrite(green,HIGH);
    delay(3000);
  }

  if (state == 3){
    digitalWrite(red,LOW);
    digitalWrite(yellow,HIGH);
    digitalWrite(green,LOW);
    delay(500);
  }
  state++;
  state = state % 4;
  
}
