# Chapulin
This project is meant to gather outside information and sensor information to know if the conditions are perfect so the insect's growth is the best that it can be.

The first thing that the program do is the setup of the actual conditions with the use of threads taking into account different things such as:
Outside and inside Temperatures (I simulate the sensors readings)
Outside and inside Humidity (I simulate the sensors readings)
Outside and inside Light
Feed (I simulate the feeding dispensers)

Every second each thread is updating a class to create the necessary calculations to have the maximum growth of insects. As each of the threads directly affects the growth of the insects, they are all constantly updating a Leslie matrix created with previous research to know the maximum growth of the cricket species.

This Leslie matrix can be affected by each variable, especially sensors and external information.

The program seeks that the farm is in optimal conditions to have the greatest growth, as we know conditions matter and every time they are getting out of the best, Leslie's matrix is affected and therefore one-year growth.

During the process, each thread prints relevant information, for example if it goes outside the expected range and what type of action should be taken and each of these is expected in the future to help self-regulate the farm, and then each time there is a change in the matrix The user changes and notifies the amount of insect that he will have in the future and his Leslie matrix as it is affected.

In order to know everything about the project documentation look everything up in this link: [Documentation](https://docs.google.com/document/d/10ctWvoUC_pm9K77vCH6CLXl31sDnE4-uvIke2oRk63M/edit?usp=sharing)
