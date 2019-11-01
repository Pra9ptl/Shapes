// This #include statement was automatically added by the Particle IDE.
#include <InternetButton.h>
InternetButton b = InternetButton();

int si = -1;

void setup() {
    b.begin();
    
    Particle.function("shapes", showShapes);
}

void loop() {
    sidesInput();
}

void sidesInput(){
    if(b.buttonOn(2)){
        b.allLedsOn(255, 255, 255);
        delay(150);
        b.allLedsOff();
        si = 0;
        Particle.publish("sid", String(si));
        delay(500);
    }
    
    if(b.buttonOn(3)){
        b.allLedsOn(255, 255, 255);
        delay(150);
        b.allLedsOff();
        si = 3;
        Particle.publish("sid", String(si));
        delay(500);
    }
    
    if(b.buttonOn(4)){
        b.allLedsOn(255, 255, 255);
        delay(150);
        b.allLedsOff();
        si = 4;
        Particle.publish("sid", String(si));
        delay(500);
    }
    
    
}

int randomNumberGenerator(int max) {
  return random(1, max+1);
}

int showShapes(String command){
    if(command == "draw"){
        int r_num = randomNumberGenerator(3);
        if(r_num == 1){ // Circle
            Particle.publish("queshape", "circle");
            b.allLedsOn(255, 255, 255);
            delay(300);
            b.allLedsOff();
        }  else if(r_num == 2) { // Square
            Particle.publish("queshape", "square");
            b.ledOn(2, 255, 0, 255);
            b.ledOn(4, 255, 0, 255);
            b.ledOn(8, 255, 0, 255);
            b.ledOn(10, 255, 0, 255);
            delay(300);
            b.allLedsOff();
        } else { // triangle
            Particle.publish("queshape", "triangle");
            b.ledOn(2, 255, 255, 0);
            b.ledOn(6, 255, 255, 0);
            b.ledOn(10, 255, 255, 0);
            delay(300);
            b.allLedsOff();
        }
    }
    return 1;
}