#define STEP_PIN 3
#define DIR_PIN 2
#define ENABLE_PIN 4

void setup() {
  pinMode(STEP_PIN, OUTPUT);
  pinMode(DIR_PIN, OUTPUT);
  pinMode(ENABLE_PIN, OUTPUT);
  
  digitalWrite(ENABLE_PIN, LOW);  // Enable the driver (active low)
  digitalWrite(DIR_PIN, HIGH);     // Set direction
}

void loop() {
  // Move the motor a certain number of steps
  for (int i = 0; i < 200; i++) {  // Adjust step count as needed
    digitalWrite(STEP_PIN, HIGH);
    delayMicroseconds(1000);        // Adjust delay for speed
    digitalWrite(STEP_PIN, LOW);
    delayMicroseconds(1000);
  }

  delay(1000);                      // Wait for a second
  
  digitalWrite(DIR_PIN, LOW);      // Change direction

  // Move back
  for (int i = 0; i < 200; i++) {
    digitalWrite(STEP_PIN, HIGH);
    delayMicroseconds(1000);
    digitalWrite(STEP_PIN, LOW);
    delayMicroseconds(1000);
  }

  delay(1000);                      // Wait for a second
}