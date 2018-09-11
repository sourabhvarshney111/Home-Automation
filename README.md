# Home Automation
The Project is based on the idea to automate Home using the algorithms of Machine Learning. The projects covers two major parts:
1. Neural Network Based Lobby
2. Command Operated Electronic Appliances
## 1. Neural Network Based Lobby:
### Aim:
Make an energy effecient lighting system for places such as lobbies and corridors such that energy at each point is above a minimum threshold, wasting minimum energy. A human analogous model was designed using the neural network algorithm.

### Approach:
The network learns the pattern of bulbs in the lobby according to the intensity values obtained by LDR sensors placed at various points and the atmospheric conditions. Accordingly the trained network gives the pattern of bulbs according to the atmospheric condition such that energy at each point is approximately equal to the threshold.

### Installations 
To use this implementation, you will require following:
1. Python 3.6
''' sudo apt-get install pip
sudo pip install python3
'''
2. Numpy: 
''' sudo apt-get install Numpy'''

3. PLX-DAQ: To save data obtained by LDR sensors as excel file. 
   Refer http://www.instructables.com/id/Sending-data-from-Arduino-to-Excel-and-plotting-it/ for installation
