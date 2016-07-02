/*§
  ===========================================================================
  LambdaPrism
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

grammar PlanningProblem;


@header {
package info.gianlucacosta.lambdaprism.planning.problem.parser;
}


problem: startDeclaration goalDeclaration mainAction*;

startDeclaration: 'START:' literals;

goalDeclaration: 'GOAL:' literals;

mainAction: 'ACTION:' signature ('PRE:' preconditions)? ('POST:' effects)?;

signature: FUNCTOR_NAME parameters?;

parameters: '(' variable (',' variable)* ')';

preconditions: literals;

effects: literals;


literals: literal (',' literal)*;

literal: NOT? functor;

functor: FUNCTOR_NAME arguments?;

arguments: '(' argument (',' argument)* ')';

argument: variable | constant;


variable: VARIABLE_NAME;

constant: FUNCTOR_NAME;

NOT: '~';

FUNCTOR_NAME: [a-z0-9][A-Za-z0-9_]*;

VARIABLE_NAME: [A-Z][A-Za-z0-9_]*;

WHITESPACE: [ \t\r\n]+ -> skip ;

SINGLE_LINE_COMMENT: ('//'|'#') ~('\r' | '\n')* -> skip;

MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;