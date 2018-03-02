Assumptions

1.	Vehicles only travel sequentially
2.	The distance between two axles is 2.5m
3.	Statistical data only feeds in Milliseconds
4.	Morning session is limited to 8AM to 11.59 and evening 12 - 4.59
5.	Survey data file only contains one day
6.	Survey data format and patters are accurate


Further Improvements

1.	Replace System.out with logger
2.	Adopt Dependency Injection

Solution:
1. The solution uses Java8 to read the survey file and process the data.
2. The file should be located under src\main\resources folder by the name "Vehicle Survey Coding Challenge sample data.txt"
