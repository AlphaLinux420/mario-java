package com.game;

public class Arduino_Sketch {

#define USE_NIMBLE
#include <BleKeyboard.h>

#define PIN_W 2
#define PIN_A 4
#define PIN_S 13
#define PIN_D 21
#define PIN_SPACE 25
#define PIN_ENTER 26
#define PIN_SPACE_LED 27
#define PIN_ENTER_LED 14
#define PIN_MODE_WASD 23
#define PIN_MODE_ARROWS 22

    int keyPins[] = { PIN_W, PIN_A, PIN_S, PIN_D, PIN_SPACE, PIN_ENTER };
    uint8_t keyCodes[] = { 'w', 'a', 's', 'd', ' ', '\n' };
    bool keyStates[] = {false, false, false, false, false, false};

    uint8_t arrowCodes[] = {218, 216, 217, 215, ' ', '\n'};
    uint8_t wasdCodes[] = { 'w', 'a', 's', 'd', ' ', '\n' };

    bool wasdMode = true;
    bool wasdModeChanged=false;

    long lastBlink = 0;
    bool blinkState = false;

    BleKeyboard bleKeyboard;

    void setup() {
        Serial.begin(115200);
        Serial.println("\nStarting BLE work!");
        setInputs();
        bleKeyboard.begin();
        pinMode(PIN_MODE_WASD,  INPUT_PULLUP);
        pinMode(PIN_MODE_ARROWS,  INPUT_PULLUP);
        pinMode(PIN_SPACE_LED, OUTPUT);
        pinMode(PIN_ENTER_LED, OUTPUT);
        pinMode(5, OUTPUT);
    }

    void loop() {
        blink();

        if (keyStates[4] && keyStates[5] && !wasdModeChanged) {
            wasdMode = !wasdMode;
            wasdModeChanged = true;
            if (wasdMode) {
                memcpy(&keyCodes, wasdCodes, 6);
            } else {
                memcpy(&keyCodes, arrowCodes, 6);
            }
        } else {
            wasdModeChanged = false;
        }

        for (int i = 0; i < 6; i++) {
            handleButton(i);
        }

    }

    void setInputs() {
        for (int i = 0; i < 6; i++) {
            pinMode(keyPins[i], INPUT_PULLUP);
        }
    }

    void blink(){
        if(millis() - lastBlink > 500){
            if(blinkState){
                digitalWrite(PIN_SPACE_LED, HIGH);
                digitalWrite(PIN_ENTER_LED, HIGH);
                digitalWrite(5, HIGH);
                blinkState = false;
            }
            else{
                digitalWrite(PIN_SPACE_LED, LOW);
                digitalWrite(PIN_ENTER_LED, LOW);
                digitalWrite(5, LOW);
                blinkState = true;
            }

            lastBlink = millis();
        }
    }

    void handleButton(int keyIndex) {
        if (!digitalRead(keyPins[keyIndex]) && !wasdModeChanged) {
            Serial.println(keyPins[keyIndex]);

            if(!keyStates[keyIndex]){
                bleKeyboard.press(keyCodes[keyIndex]);
                keyStates[keyIndex] = true;
            }
        }
        else{
            if(keyStates[keyIndex]){
                bleKeyboard.release(keyCodes[keyIndex]);
                keyStates[keyIndex] = false;
            }
        }
    }
}
