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

/**
  * A basic decision tree, having just one leaf level
  */
trait DecisionTree {

  /**
    * The original problem
    *
    * @return
    */
  def problem: ClassificationProblem


  /**
    * The attribute on which the tree is based
    *
    * @return
    */
  def attribute: Attribute

  /**
    * The map <i>attribute value - tree leaf</i>
    *
    * @return
    */
  def leaves: Map[Value, DecisionTreeLeaf]


  /**
    * Classifies an instance having the given attribute value - thus returning
    * a map telling the probability associated with each output value.
    *
    * @param attributeValue The attribute value. It can also be the unknown-value placeholder
    * @return The map of probabilities
    */
  def classify(attributeValue: Value): Map[Value, Double]
}
