#include <Arduino.h>

// Define pin numbers for Motor 1
const int stepPin1 = 3;  // Connect to the step pin of the first driver
const int dirPin1 = 4;   // Connect to the direction pin of the first driver

// Define pin numbers for Motor 2
const int stepPin2 = 5;  // Connect to the step pin of the second driver
const int dirPin2 = 6;   // Connect to the direction pin of the second driver

// Define step size
const int stepSize = 500; // Number of steps to move at a time
const int baseDelay = 500; // Base delay in microseconds for faster movement

void setup() {
  // Set pin modes for Motor 1
  pinMode(stepPin1, OUTPUT);
  pinMode(dirPin1, OUTPUT);

  // Set pin modes for Motor 2
  pinMode(stepPin2, OUTPUT);
  pinMode(dirPin2, OUTPUT);

  // Move both motors to starting position (set direction)
  digitalWrite(dirPin1, HIGH); // Set direction for Motor 1
  digitalWrite(dirPin2, HIGH); // Set direction for Motor 2
  moveSteppers(stepSize); // Move both motors to the initial position
}

void loop() {
  // Move both motors from X to Y
  digitalWrite(dirPin1, HIGH); // Set direction for Motor 1
  digitalWrite(dirPin2, HIGH); // Set direction for Motor 2
  moveSteppers(stepSize); // Move both motors forward
  
  delay(2000); // Wait for 2 seconds

  // Move both motors back from Y to X
  digitalWrite(dirPin1, LOW); // Set direction for Motor 1
  digitalWrite(dirPin2, LOW); // Set direction for Motor 2
  moveSteppers(stepSize); // Move both motors backward

  delay(2000); // Wait for 2 seconds before repeating
}

void moveSteppers(int steps) {
  for (int i = 0; i < steps; i++) {
    // Gradually decrease delay for faster movement
    int delayTime = baseDelay * (1 - (float)i / steps); // Ramp up speed

    // Step Motor 1
    digitalWrite(stepPin1, HIGH); // Trigger the step pin for Motor 1
    delayMicroseconds(delayTime); // Use ramped speed
    digitalWrite(stepPin1, LOW); // Reset the step pin for Motor 1
    delayMicroseconds(delayTime); // Use ramped speed

    // Step Motor 2
    digitalWrite(stepPin2, HIGH); // Trigger the step pin for Motor 2
    delayMicroseconds(delayTime); // Use ramped speed
    digitalWrite(stepPin2, LOW); // Reset the step pin for Motor 2
    delayMicroseconds(delayTime); // Use ramped speed
  }
}
