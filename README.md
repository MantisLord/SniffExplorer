# SniffExplorer
Tool to more easily analyze and acquire relevant data contained in sniffs.

###### Backstory
In order to improve the quality of the AI scripts in the [TrinityCore Project]( https://github.com/TrinityCore/TrinityCore), I have been working a lot with sniffs lately. Numerous hours are wasted because the only tools available to go through hundreds of thousands of packets and literally millions lines of text are a notepad and its Ctrl-F function. I realized that this whole “data mining” process could be greatly improved with the right tools. The text files produced by the WPP are a great source of data but this data is basically surrounded by trash information. A software capable of filtering packets based on the user criterias in real time would be ideal. Such tool would be able to, for example, only show the packets where this one particular boss (identified either by GUID or creature entry) is casting a spell. Or show every movement packet of a certain creature between time X and time Y. Or, etc... The possibilities are endless. Such tool will offer a highly modular way to make searches, in real time, easily. Sounds pretty cool right? : )

Well, I started to work on it and I named it SniffExplorer. **Its main goal is to make a much better use of the data contained in sniffs.**

#### You wish to explore the code and possibly contribute?

The project is written in Java and any contribution is more than welcome.
**If you are starting here and wish to understand how the project works**, I recommend that you start by reading [the life cycle of the application](https://github.com/chaodhib/SniffExplorer/blob/master/Analysis%20Documents/life%20cycle%20of%20the%20application.txt) and then look at the class diagram and the “todo” list in the [analysis folder]( https://github.com/chaodhib/SniffExplorer/tree/master/Analysis%20Documents).

For any discussion about the software, see its post in the forum here: http://community.trinitycore.org/topic/11952-sniffexplorer/
