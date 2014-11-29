
namespace Controller
{
	struct ButtonStruct
	{
		unsigned long eventCount; //event counter, increases with every controller event,
		//but for some reason not by one.
		unsigned short up : 1, down : 1, left : 1, right : 1, start : 1, back : 1, l3 : 1, r3 : 1,
		lButton : 1, rButton : 1, guideButton : 1, unknown : 1, aButton : 1,
			  bButton : 1, xButton : 1, yButton : 1; // button state bitfield
		unsigned char l2;  //Left Trigger
		unsigned char r2;  //Right Trigger
		short leftStickX; 
		short leftStickY; 
		short rightStickX;
		short rightStickY;
	};

	typedef int(__stdcall * pICFUNC)(int, ButtonStruct &);
	typedef int(__stdcall * pc)(int);

	class Controller
    {
    public:
		int initController(void);
		void startVibration(int controller, int leftMotor, int rightMotor);
		void stopVibration(int controller);
		void turnControllerOff(int controller);
		void close(void);
		void getBatteryState(int controller);
		void populateControllerData(int controller, int buttons[]);
    };
}