<h1>Issue #146 Bug</h1>

<p>You can start a REPL with the default profile, require the fixtures namespace and run the pre-test function (this one depends on Cheshire by way of example):</p>

```clojure
(! 520)-> clj -A:dev:test:+default
Clojure 1.10.3
user=> (require 'issue146.example.fixtures)
nil
user=> (issue146.example.fixtures/pre-test)
example-one with cheshire
nil
user=> ^D
```

<p>Similarly, you can start a REPL with the default profile, require the fixtures namespace and run the pre-test function (this has no dependencies):</p>

```clojure
(! 521)-> clj -A:dev:test:+other
Clojure 1.10.3
user=> (require 'issue146.example.fixtures)
nil
user=> (issue146.example.fixtures/pre-test)
example-two
nil
user=>
```

<p>`check` thinks it is valid:</p>

```clojure
(! 522)-> clojure -M:poly check
OK
```

<p>If you run the tests for the `other` profile, it all works:</p>

```clojure
(! 523)-> clojure -M:poly test :all :dev +other
Projects to run tests from: development

Running tests from the development project, including 1 brick: example-two

Testing issue146.example.interface-test

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.

Test results: 1 passes, 0 failures, 0 errors.

Testing issue146.example.fixtures

Ran 0 tests containing 0 assertions.
0 failures, 0 errors.

Test results: 0 passes, 0 failures, 0 errors.

Execution time: 1 seconds
```

<p>However, if you run the tests for the `default` profile, it fails because the Cheshire dependency does not get onto the classpath:</p>

```clojure
(! 524)-> clojure -M:poly test :all :dev +default
Projects to run tests from: development

Running tests from the development project, including 1 brick: example-one

Testing issue146.example.interface-test

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.

Test results: 1 passes, 0 failures, 0 errors.
java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at polylith.clj.core.common.class_loader$invoke_in_STAR_.invokeStatic(class_loader.clj:31)
	at polylith.clj.core.common.class_loader$invoke_in_STAR_.doInvoke(class_loader.clj:27)
	at clojure.lang.RestFn.invoke(RestFn.java:494)
	at polylith.clj.core.common.class_loader$eval_in_STAR_$print_read_eval__287.invoke(class_loader.clj:49)
	at polylith.clj.core.common.class_loader$eval_in_STAR_.invokeStatic(class_loader.clj:51)
	at polylith.clj.core.common.class_loader$eval_in_STAR_.invoke(class_loader.clj:45)
	at polylith.clj.core.common.class_loader$eval_in.invokeStatic(class_loader.clj:61)
	at polylith.clj.core.common.class_loader$eval_in.invoke(class_loader.clj:60)
	at polylith.clj.core.common.interface$eval_in.invokeStatic(interface.clj:22)
	at polylith.clj.core.common.interface$eval_in.invoke(interface.clj:21)
	at polylith.clj.core.test_runner.core$run_test_statements$fn__8485.invoke(core.clj:57)
	at polylith.clj.core.test_runner.core$run_test_statements.invokeStatic(core.clj:56)
	at polylith.clj.core.test_runner.core$run_test_statements.invoke(core.clj:50)
	at polylith.clj.core.test_runner.core$run_tests_for_project.invokeStatic(core.clj:116)
	at polylith.clj.core.test_runner.core$run_tests_for_project.invoke(core.clj:95)
	at polylith.clj.core.test_runner.core$run.invokeStatic(core.clj:162)
	at polylith.clj.core.test_runner.core$run.invoke(core.clj:146)
	at polylith.clj.core.test_runner.interface$run.invokeStatic(interface.clj:5)
	at polylith.clj.core.test_runner.interface$run.invoke(interface.clj:4)
	at polylith.clj.core.command.test$run.invokeStatic(test.clj:8)
	at polylith.clj.core.command.test$run.invoke(test.clj:5)
	at polylith.clj.core.command.core$execute.invokeStatic(core.clj:86)
	at polylith.clj.core.command.core$execute.invoke(core.clj:62)
	at polylith.clj.core.command.interface$execute_command.invokeStatic(interface.clj:5)
	at polylith.clj.core.command.interface$execute_command.invoke(interface.clj:4)
	at polylith.clj.core.poly_cli.core$_main.invokeStatic(core.clj:33)
	at polylith.clj.core.poly_cli.core$_main.doInvoke(core.clj:7)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clojure.lang.Var.applyTo(Var.java:705)
	at clojure.core$apply.invokeStatic(core.clj:667)
	at clojure.main$main_opt.invokeStatic(main.clj:514)
	at clojure.main$main_opt.invoke(main.clj:510)
	at clojure.main$main.invokeStatic(main.clj:664)
	at clojure.main$main.doInvoke(main.clj:616)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clojure.lang.Var.applyTo(Var.java:705)
	at clojure.main.main(main.java:40)
Caused by: Syntax error compiling at (issue146/example/fixtures.clj:1:1).
	at clojure.lang.Compiler.load(Compiler.java:7652)
	at clojure.lang.RT.loadResourceScript(RT.java:381)
	at clojure.lang.RT.loadResourceScript(RT.java:372)
	at clojure.lang.RT.load(RT.java:459)
	at clojure.lang.RT.load(RT.java:424)
	at clojure.core$load$fn__6856.invoke(core.clj:6115)
	at clojure.core$load.invokeStatic(core.clj:6114)
	at clojure.core$load.doInvoke(core.clj:6098)
	at clojure.lang.RestFn.invoke(RestFn.java:408)
	at clojure.core$load_one.invokeStatic(core.clj:5897)
	at clojure.core$load_one.invoke(core.clj:5892)
	at clojure.core$load_lib$fn__6796.invoke(core.clj:5937)
	at clojure.core$load_lib.invokeStatic(core.clj:5936)
	at clojure.core$load_lib.doInvoke(core.clj:5917)
	at clojure.lang.RestFn.applyTo(RestFn.java:142)
	at clojure.core$apply.invokeStatic(core.clj:669)
	at clojure.core$load_libs.invokeStatic(core.clj:5974)
	at clojure.core$load_libs.doInvoke(core.clj:5958)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clojure.core$apply.invokeStatic(core.clj:669)
	at clojure.core$require.invokeStatic(core.clj:5996)
	at clojure.core$require.doInvoke(core.clj:5996)
	at clojure.lang.RestFn.invoke(RestFn.java:408)
	at clojure.core$eval175.invokeStatic(NO_SOURCE_FILE:0)
	at clojure.core$eval175.invoke(NO_SOURCE_FILE)
	at clojure.lang.Compiler.eval(Compiler.java:7181)
	at clojure.lang.Compiler.eval(Compiler.java:7170)
	at clojure.lang.Compiler.eval(Compiler.java:7136)
	at clojure.core$eval.invokeStatic(core.clj:3202)
	at clojure.core$eval.invoke(core.clj:3198)
	at clojure.core$eval171.invokeStatic(NO_SOURCE_FILE:0)
	at clojure.core$eval171.invoke(NO_SOURCE_FILE)
	at clojure.lang.Compiler.eval(Compiler.java:7181)
	at clojure.lang.Compiler.eval(Compiler.java:7136)
	... 41 more
Caused by: java.io.FileNotFoundException: Could not locate cheshire/core__init.class, cheshire/core.clj or cheshire/core.cljc on classpath.
	at clojure.lang.RT.load(RT.java:462)
	at clojure.lang.RT.load(RT.java:424)
	at clojure.core$load$fn__6856.invoke(core.clj:6115)
	at clojure.core$load.invokeStatic(core.clj:6114)
	at clojure.core$load.doInvoke(core.clj:6098)
	at clojure.lang.RestFn.invoke(RestFn.java:408)
	at clojure.core$load_one.invokeStatic(core.clj:5897)
	at clojure.core$load_one.invoke(core.clj:5892)
	at clojure.core$load_lib$fn__6796.invoke(core.clj:5937)
	at clojure.core$load_lib.invokeStatic(core.clj:5936)
	at clojure.core$load_lib.doInvoke(core.clj:5917)
	at clojure.lang.RestFn.applyTo(RestFn.java:142)
	at clojure.core$apply.invokeStatic(core.clj:669)
	at clojure.core$load_libs.invokeStatic(core.clj:5974)
	at clojure.core$load_libs.doInvoke(core.clj:5958)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clojure.core$apply.invokeStatic(core.clj:669)
	at clojure.core$require.invokeStatic(core.clj:5996)
	at clojure.core$require.doInvoke(core.clj:5996)
	at clojure.lang.RestFn.invoke(RestFn.java:408)
	at issue146.example.fixtures$eval179$loading__6737__auto____180.invoke(fixtures.clj:1)
	at issue146.example.fixtures$eval179.invokeStatic(fixtures.clj:1)
	at issue146.example.fixtures$eval179.invoke(fixtures.clj:1)
	at clojure.lang.Compiler.eval(Compiler.java:7181)
	at clojure.lang.Compiler.eval(Compiler.java:7170)
	at clojure.lang.Compiler.load(Compiler.java:7640)
	... 74 more
Couldn't run test statement for the development project: (do (clojure.core/use (quote clojure.test)) (clojure.core/require (quote issue146.example.fixtures)) (clojure.test/run-tests (quote issue146.example.fixtures))) java.lang.reflect.InvocationTargetException

Test results:  passes,  failures,  errors.
```
