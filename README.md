# SniffExplorer
Tool to better analyze and explore data contained in sniffs

###### Backstory
In order to improve one of the AI boss scripts, I have been working a lot with sniffs lately. After numerous hours wasted because the only tool available to go through hundreds of thousands of packets and literally millions of lines was Ctrl-F, I realized that this whole mining for data process could be greatly improved with the right tool. The text files produced by WPP are a great source of data but this data is basically surrounded by trash information. A software that would filter the shown packets based on the user criterias in real time would be ideal. Such tool would be able to, for example, only show the packets where this one particular mob (identified either by GUID or creature entry) is the source of any spell. Or show every movement packet of every creature between time X and time Y. Or, etc... The possibilities are endless. Such tool will offer a highly modular way to make searches, in real time, easily. Sounds pretty cool right? : )

This is how this software came to life: It aims at dynamically filter the data contained in sniff and show them, therefore more efficiently use the sniffs. It's written in Java and I'm gladly welcoming contributors.

#### You wish to explore the code and contribute ?

**If you are starting here and wish to understand how the project works**, I recommand that you start by reading [the life cycle of the application](http://github.com) and then look at the class diagram and the todo text file in the analysis folder: https://github.com/chaodhib/SniffExplorer/tree/master/Analysis%20Documents

For any discusion about the software, see it's post in the forum here: http://community.trinitycore.org/topic/11952-sniffexplorer/
