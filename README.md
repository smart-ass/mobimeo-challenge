## mobimeo-challenge

The app is implemented in Scala/Play framework.

To run the app, execute `sbt run`

To run the test, execute `sbt test`

### Some notes for reviewers

The play framework is not the most lightweight framework in scala world and probably too big for such a small task.
I only chose it for the sake of saving time, since I know it better than other technology.

Tests are very basic and mostly cover the happy paths. 
In the real app testing should be more thorough.

Things that could be done better:
 * Data validation. Depending on our requirements and data quality, we could implement more strict global validation
 (check that data in different files are consistent) or being more resilient to imperfect data
 * Abstraction over Repo. In this task there're only csv based implementations. In the real app we may need to
 have a basic interface with a few inheritors (csv based, db based, etc) 