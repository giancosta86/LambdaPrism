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

package info.gianlucacosta.lambdaprism.classification.basic

import org.scalatest.{FlatSpec, Matchers}

class TestEntropy extends FlatSpec with Matchers {
  "Entropy" should "be 0 when there is a single group" in {
    val testData =
      List(
        List("A", "X"),
        List("B", "X"),
        List("C", "X"),
        List("D", "X"),
        List("E", "X"),
        List("F", "X")
      )

    val entropy =
      Entropy.compute(testData)

    entropy should be(0)
  }


  "Entropy" should "be 1 when all groups are equally sized" in {
    val testData =
      List(
        List("A", "X"),
        List("B", "X"),
        List("C", "X"),
        List("D", "Y"),
        List("E", "Y"),
        List("F", "Y")
      )

    val entropy =
      Entropy.compute(testData)

    entropy should be(1)
  }


  "Entropy" should "be correct in an example case" in {
    val testData =
      List(
        List("A", "X"),
        List("B", "X"),
        List("C", "X"),
        List("D", "X"),
        List("E", "Y"),
        List("F", "Y")
      )

    val entropy =
      Entropy.compute(testData)

    entropy should be(0.91829583 +- 0.000000009)
  }
}
