# SniffExplorer
Tool to more easily analyze and acquire relevant data contained in sniffs.

#### Current example of output
https://gist.github.com/chaodhib/27d293e51b269545edb8#file-gistfile1-txt

#### How to use

Warning, the project is still in a very early version and not many functionalities are working. 

You need Java 8 installed. Right now, to use the program, put a parsed sniff file produced by WPP in the base folder and renamme it to "sniff.txt". Start the application. Wait around 5-10s, it will then produce the output.txt file.

There is no way to modify the filter right now other than changing it in the code.

#### You wish to explore the code and possibly contribute?

The progression and more documents can be found here:
https://docs.google.com/spreadsheets/d/17NXOHlyx4Nce3DbCgIF9WEjKOkcb-Cxd4U1Ygon71iY/edit?usp=sharing

The project is written in Java and any contribution is more than welcome.
**If you are starting here and wish to understand how the project works**, I recommend that you start by reading [the life cycle of the application](https://github.com/chaodhib/SniffExplorer/blob/master/Analysis%20Documents/life%20cycle%20of%20the%20application.txt) and then look at the class diagram and the “todo” list in the [analysis folder]( https://github.com/chaodhib/SniffExplorer/tree/master/Analysis%20Documents).

For any discussion about the software, see its post in the forum here: http://community.trinitycore.org/topic/11952-sniffexplorer/

#### Backstory
In order to improve the quality of the AI scripts in the [TrinityCore Project]( https://github.com/TrinityCore/TrinityCore), I have been working a lot with sniffs lately. Numerous hours are wasted because the only tools available to go through hundreds of thousands of packets and literally millions lines of text are a notepad and its Ctrl-F function. I realized that this whole “data mining” process could be greatly improved with the right tools. The text files produced by the WPP are a great source of data but this data is basically surrounded by trash information. A software capable of filtering packets based on the user criterias in real time would be ideal. Such tool would be able to, for example, only show the packets where this one particular boss (identified either by GUID or creature entry) is casting a spell. Or show every movement packet of a certain creature between time X and time Y. Or, etc... The possibilities are endless. Such tool will offer a highly modular way to make searches, in real time, easily. Sounds pretty cool right? : )

Well, I started to work on it and I named it SniffExplorer. **Its main goal is to make a much better use of the data contained in sniffs.**
