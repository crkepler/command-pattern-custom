# command-pattern-custom
Command pattern reference implementation. Custom implementation for a rules engine like execution.

## 1. Setup and Installation

* Java 7 - it'll very likely work with earlier versions, it's just that we haven't tested it.
* Maven 3 or later


### Compiling
````
>mvn clean install
````
### Running
````
>mvn exec:java
````
## 2. The Custom Command Pattern

This implementation is customized to execute commands in a "rules" like implementation with 3 different execution stages:

1. <strong>Invoker</strong> - returns a ````List<Evaluator>```` where the parent abstract class iterates and fires ````execute()```` on each Evaluator item in the list. Its main job is to invoke Evaluators (below).</br></br>
2. <strong>Evaluator</strong> - returns a ````List<Command>```` where the parent abstract class iterates and fires ````execute()```` for each Command in the list. Its main job is to place conditionals before each command is executed. For example, it won't add a command to the ````List<Command>```` to be executed because the concrete class condition returned false. This is a great way to place "ifs" statements outside the commands themselves (below).</br></br>
3. <strong>Command</strong> - the concret command class implements an ````execute()```` method which is called by the Evaluator. When called, it will always execute, leaving conditions to be checked by the Evaluator.

### Benefits
* It eliminates or significantly reduces procedural code.
* Increases cohesion (well defined classes) and eliminates tight coupling.
* Increases testability.
* Improves readability, maintenability, and extensibility. It makes it easier to innovate and improve your code base without fearing breaking something else (high cohesion, low coupling).
* Context driven execution. The context is clearly defined and returned at the end of the last invoker, with all populated values.

### Use Cases
* Rules engine
* Chained calculations (e.g. insurance quotes, inventory re-ordering, credit limits, etc.)
* Plug-in like implementations (e.g. each command can call a separate plug-in).
* Many more, these are just examples 


## 3. To Do

* Create unit tests
* More concrete examples
