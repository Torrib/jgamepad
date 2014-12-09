
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <linux/joystick.h>
#include <pthread.h>

int run = 1;
int controller_status[4][21] = {0};
int fd[4];
struct js_event jse;
int buttons = 21;

typedef struct {
	int controller;
}controller_arg;

int open_joystick(int joystick)
{
	int result = -1;

	char device[9];
	sprintf(device, "/dev/input/js%d", joystick);
	
	result = open(device, O_RDONLY | O_NONBLOCK);

	if(result < 0)
	{
		printf("Unable to open %s\n", device);
	}
	return result;
}

update_controller_status(int controller, struct js_event jse)
{
	int index;

	if(jse.type == 1) //Button
	{
		//rearranging the buttons to match Xinput
		
		if(jse.number == 6)
			index = 7;
		else if(jse.number == 7)
			index = 6;
		else if(jse.number == 8)
			index = 10;		
		else if(jse.number > 8 && jse.number < 11)
			index = jse.number -1;
		else
			index = jse.number;

		controller_status[controller][index] = jse.value;
	}
	else if(jse.type == 2) //Analog
	{
		index = jse.number + 15;
		controller_status[controller][index] = jse.value;
	}
}

void* update(void *arg)
{
	controller_arg *args = arg;
	int controller = args->controller;
	while(run == 1)
	{
		while(read(fd[controller], &jse, sizeof(jse)) > 0)
		{
			update_controller_status(controller, jse);
		}
		usleep(50);
	}
}

int initController()
{
	int i;
	pthread_t pid[4];
	
	for(i = 0; i < 4; i++){
		fd[i] = open_joystick(i +1);

		controller_arg *args = malloc(sizeof *args);
		args->controller = i;

		pthread_create(&(pid[i]), NULL, &update, args);
	}
	return 1;
}

populateControllerData(int controller, int *data)
{
	int i;
	for(i = 0; i < buttons; i++){
		data[i] = controller_status[controller][i];
	}
}

startVibration(int controller, int leftMotor, int rightMotor){
	printf("Not implemented yet\n");
}

stopVibration(int controller){
	printf("Not implemented yet\n");
}

turnControllerOff(int controller){
	printf("Not implemented yet\n");
}




	/*
	FILE *fp;
 	char path[1035];

	int result = system("sudo xboxdrv");
	printf("Result: %d\n", result);

	fp = popen("ls", "r");
  	if (fp == NULL) {
    		printf("Failed to run command\n" );
    		exit(1);
  	}

	while (fgets(path, sizeof(path)-1, fp) != NULL) {
    		printf("%s", path);
  	}

  	pclose(fp);
	*/
