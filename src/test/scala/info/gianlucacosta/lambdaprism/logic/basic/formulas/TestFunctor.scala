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

import org.scalatest.{FlatSpec, Matchers}

class TestFunctor extends FlatSpec with Matchers {
  "Constants" should "be sorted according to their name" in {
    val left =
      Constant("f")

    val right =
      Constant("g")

    (left < right) should be(true)
  }



  "A constant" should "be sorted before a compound functor having its same name" in {
    val left =
      Constant("f")

    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("A")
        )
      )

    (left < right) should be(true)
  }


  "f(A)" should "be sorted before F(A, B)" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A")
        )
      )

    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("B")
        )
      )

    (left < right) should be(true)
  }


  "f(A, B)" should "be sorted before g(A)" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("B")
        )
      )

    val right =
      CompoundFunctor(
        "g",
        List(
          Variable("A")
        )
      )

    (left < right) should be(true)
  }
}
