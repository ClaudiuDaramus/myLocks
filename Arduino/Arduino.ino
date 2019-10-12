#define TIMEOUT 1000
#define BLUETOOTH_BAUD_RATE 9600
#define AT_BAUD_RATE 38400

const int bluetoothKey = 3;

String readBluetoothString() {
  String result = "";
  long start = millis();
  while(!Serial.available() && millis() - start < TIMEOUT);
  char c;
  do {
    c = Serial.read();
    result += c;
  } while(millis() - start < TIMEOUT && c != '\n');
  return result;
}

bool changePass(String pass) {
  digitalWrite(bluetoothKey, HIGH);
  Serial.begin(AT_BAUD_RATE);
  
  Serial.print("AT+PSWD=\"");
  Serial.print(pass);
  Serial.print("\"");
  
  digitalWrite(bluetoothKey, LOW);
  Serial.begin(BLUETOOTH_BAUD_RATE);

  String response = "";
  response = Serial.readString();
  if(response != "OK") return false;
  return true;
}

bool changeName(String name) {
  digitalWrite(bluetoothKey, HIGH);
  Serial.begin(AT_BAUD_RATE);
  
  Serial.print("AT+NAME=\"");
  Serial.print(name);
  Serial.print("\"");
  
  digitalWrite(bluetoothKey, LOW);
  Serial.begin(BLUETOOTH_BAUD_RATE);

  String response = "";
  response = Serial.readString();
  if(response != "OK") return false;
  return true;
}

void setup() {
  // put your setup code here, to run once:
  Serial.begin(BLUETOOTH_BAUD_RATE);
  pinMode(bluetoothKey, OUTPUT);
  digitalWrite(bluetoothKey, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()){
    int command = Serial.read();
    switch (command) {
      case 10:
        // Change password
        String pass = readBluetoothString();
        if(pass != "") changePass(pass);
        break;
      case 11:
        // Change device name
        String name = readBluetoothString();
        if (name != "") changeName(name);
    }
  }
}
