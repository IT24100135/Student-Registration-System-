#ifndef GLOBAL_H
#define GLOBAL_H

#include <Arduino.h>  // Ensure you include this to use Arduino types

// Function declarations
void new_turn_countdown();        // Countdown function
void handle_sequence();           // Renamed to avoid conflict with variable `sequence`
void manage_game_mode();          // Renamed to avoid conflict with variable `game_mode`
bool detect_human_movement();     // Function returns a boolean
void player_displacement();       // Player movement function
void handle_no_valid_move();      // Renamed to avoid conflict with variable `no_valid_move`

// Variable declarations
extern int trolley_coordinate_X;  // Ensure consistent data type across the code
extern int trolley_coordinate_Y;

extern unsigned long timer;       // Keep this as unsigned long
extern byte second; 
extern byte minute; 
extern byte second_white; 
extern byte minute_white; 
extern byte second_black; 
extern byte minute_black;

// Ensure both declarations have the same size
extern int reed_sensor_status[4][8];           // Array size should be consistent in the code
extern int reed_sensor_status_memory[4][8];    // Array size should be consistent in the code

// Global Variables
extern boolean new_turn_active;   // Renamed to avoid conflict
extern byte game_mode;            // Keep this as extern, should match its type
extern boolean no_valid_move;     // Keep this as extern, should match its type
extern byte sequence;             // Keep this as extern, should match its type

// Chessboard
extern int reed_sensor_record[8][8];   // Declared as extern for chessboard sensor data
extern byte trolley_coordinate_X;      // Declared as extern, ensure consistent data type
extern byte trolley_coordinate_Y;
extern char mov[4];
extern byte reed_colone[2];
extern byte reed_line[2];

void lcd_display();  // LCD display function

// Game settings
enum {start_up, start, calibration, player_white, player_black};
extern byte sequence;  // Declared as extern

enum {T_B, B_T, L_R, R_L, LR_BT, RL_TB, LR_TB, RL_BT, calibrate_speed};  // Directions
extern byte game_mode;  // Declared as extern
enum {HvsH, HvsC};

// Electromagnet
const byte MAGNET = 6;  // Magnet pin

// Countdown
extern unsigned long timer; 
extern boolean start_black;         // Declared as extern
extern boolean new_turn_active;     // Renamed to avoid conflict

// Motor
const byte MOTOR_WHITE_DIR = 2;  
const byte MOTOR_WHITE_STEP = 3;
const byte MOTOR_BLACK_DIR = 4;
const byte MOTOR_BLACK_STEP = 5;
const byte SQUARE_SIZE = 195;
const int SPEED_SLOW = 3000;  
const int SPEED_FAST = 1000;

// Multiplexer
const byte MUX_ADDR[4] = {A3, A2, A1, A0};
const byte MUX_SELECT[4] = {13, 9, 8, 7};
const byte MUX_OUTPUT = 12;  
const byte MUX_CHANNEL[16][4] = {
    {0, 0, 0, 0},
    {1, 0, 0, 0},
    {0, 1, 0, 0},
    {1, 1, 0, 0},
    {0, 0, 1, 0},
    {1, 0, 1, 0},
    {0, 1, 1, 0},
    {1, 1, 1, 0},
    {0, 0, 0, 1},
    {1, 0, 0, 1},
    {0, 1, 0, 1},
    {1, 1, 0, 1},
    {0, 0, 1, 1},
    {1, 0, 1, 1},
    {0, 1, 1, 1},
    {1, 1, 1, 1}
};

// Button - Switch
const byte BUTTON_WHITE_SWITCH_MOTOR_WHITE = 11;
const byte BUTTON_BLACK_SWITCH_MOTOR_BLACK = 10;
enum {WHITE, BLACK};

// MicroMax
extern char lastH[], lastM[];  // Declared as extern for MicroMax chess AI

#endif // GLOBAL_H
