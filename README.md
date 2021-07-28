# Light-up
UWA java practice project: A puzzle game light-up (Akari)   

Professor: Lyndon While

Developed using Bluej  

<img src="miscellaneous\image-20210728100044928.png" alt="image-20210728100044928" width=300 /><img src="miscellaneous\image-20210728100349952.png" alt="image-20210728100349952" width=300 />



```java
Akari akari1 = new Akari();
AkariViewer akariv1 = new AkariViewer(akari1);
```



more puzzles see [Puzzle](Puzzles/)  

You can create customize your own puzzle:  

A puzzle file always has seven lines. The layout of all files is as follows.  

- The first line holds the size of the board.  
- The second line holds the locations of blank black squares on the board. These locations are denoted by one string for each row that has this type of square. For example, the string *rwxyz* means that these squares occur at *r,w*; at *r,x*; at *r,y*; and at *r,z*.  
- The third line holds the locations of 0-numbered black squares on the board, denoted the same way.  
- The fourth line holds the locations of 1-numbered black squares on the board, and so on for the remaining lines.  

<img src="miscellaneous\image-20210728101601134.png" alt="image-20210728101601134" width=300 />



more infos: https://www.puzzle-light-up.com/  





