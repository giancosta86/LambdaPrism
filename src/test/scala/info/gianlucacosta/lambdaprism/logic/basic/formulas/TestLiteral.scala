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

package info.gianlucacosta.lambdaprism.logic.basic.formulas

class TestLiteral extends TestFormula[Literal] {
  "A constant" should "be positive by default" in {
    Constant("a").isPositive should be(true)
  }


  "A variable" should "be positive by default" in {
    Variable("A").isPositive should be(true)
  }


  "A compound functor" should "be positive by default" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("B")
      )
    ).isPositive should be(true)
  }



  "Inverting a positive literal" should "return a negative literal" in {
    (~Constant("a")).isPositive should be(false)
    ~Constant("a") should not be Constant("a")
  }


  "Inverting a negative literal" should "return a positive literal" in {
    (~(~Variable("A"))) should be(Variable("A"))
  }


  override protected def createGroundFormula(): Literal =
    ~Constant("a")


  override protected def createNonGroundFormula(): Literal =
    CompoundFunctor(
      "f",

      List(
        Constant("a"),
        Variable("X"),
        Constant("b")
      )
    )
}
