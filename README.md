FactBench
=====

*FactBench* is a multilingual benchmark for the evaluation of fact validation algorithms. All facts in *FactBench* are scoped with a timespan in which they were true, enableing the validation of temporal relation extraction algorithms. *FactBench* currently supports english, german and french.
You can get the current release [here](https://github.com/AKSW/FactBench/releases/tag/1.0).

*FactBench* is a set of [RDF](http://www.w3.org/TR/rdf-primer/) models. Each model contains a singular fact and the timespan in which it holds true. *FactBench* consists of a **train set**, a **test set** and a set of auxilliary files which were needed to create the benchmark.

##Which relations does *FactBench* contain?
*FactBench* provides data for 10 relations. The data was automatically extracted from Wikipedia (DBpedia respectivly) and Freebase. 

<table>
    <tr>
      <td>#</td><td>property</td><td>description</td><td>type</td><td>KB</td>
    </tr>
    <tr><td>1</td><td>award</td><td>persons who received a nobel prize</td><td>timepoint</td><td>Freebase</td></tr>
    <tr><td>2</td><td>birth</td><td>birth place and date of a person</td><td>timepoint</td><td>DBPedia</td></tr>
    <tr><td>3</td><td>death</td><td>death place and date of a person</td><td>timepoint</td><td>DBPedia</td></tr>
    <tr><td>4</td><td>foundationPlace</td><td>place and date of a company's foundation</td><td>timepoint</td><td>Freebase</td></tr>
    <tr><td>5</td><td>leader</td><td>presidents of countries</td><td>timespan</td><td>DBPedia</td></tr>
    <tr><td>6</td><td>nbateam</td><td>team associations of NBA players</td><td>timespan</td><td>DBPedia</td></tr>
    <tr><td>7</td><td>publicationDate</td><td>author of a book and it's publication date</td><td>timepoint</td><td>Freebase</td></tr>
    <tr><td>8</td><td>spouse</td><td>marriage of two persons </td><td>timespan</td><td>Freebase</td></tr>
    <tr><td>9</td><td>starring</td><td>actors who starred in films</td><td>timepoint</td><td>DBPedia</td></tr>
    <tr><td>10</td><td>subsidiary</td><td>companies and their subsidiaries</td><td>timepoint</td><td>Freebase</td></tr>
</table>
   
The granularity of *FactBench*'s time information is year. This means that a timespan is an interval of two years, e.g. 2008 - 2012. A timepoint is considered as a timespan with the same start and end year, e.g. 2008 - 2008.

##How is FactBench structured?
*FactBench* is divided in a train and in a test set (of facts). Typically you should use the train set to fit your algorithm to the given problem and evaluate you configuration on the test set. It is highly recommended not to debug/improve your algorithm on the test data. 

###Positive Examples
In general, we use facts contained in DBpedia and Freebase as positive examples. For each of the properties we consider, we generated positive examples by issuing a SPARQL or MQL query and selecting the top *150* results. Note that the results in Freebase (MQL) are ordered by an internal [relevance score](http://wiki.freebase.com/wiki/Search_Cookbook). The results for the DBpedia SPARQL queries were ordered by the number of inbound-links of a given resources' wikipedia page. We collected a total of 1500 correct statements (750 in test and train set). 
Each relation has 150 correct facts distributed equally in the test and train set.

###Negative Examples
The generation of negative examples is more involved than the generation of positive examples. In order to effectively train any fact validation algorithm, we considered it essential that many of the negative examples are similar to true statements.
In particular, most statements should be meaningful triples.
For this reason, we derive negative examples from positive examples by modifying them while still following domain and range restrictions.
Assume the input triple and the corresponding timespan **(s, p, o)(from,to)** in a knowledge base **K** is given and let **S** be the set of all subjects, **O** the set of all objects of the given relation **p** and **P** the set of all properties.
We used the following methods to generate the negative example sets dubbed *subject*, *object*, *subject-object*, *property*, *random*, *20%mix* and *date* (in that order):

 * A triple **(s',p,o)** is generated where **s'** is a random element of **S**, the triple **(s',p,o)** is not contained in **K**.
 * A triple **(s,p,o')** is generated analogously by selecting a random element of **O**.
 * A triple **(s',p,o')** is generated analogously by selecting a random **s'** from **S** and a random **o'** from **O**. 
 * A triple **(s,p',o)** is generated in which **p'** is randomly selected from the list of all properties and **(s,p',o)** is not contained in **K** and **p = p'** is allowed. 
 * A triple **(s',p',o')** is generated where **s'** and **o'** are randomly selected resources, **p'** is a randomly selected property from the list of all properties and **(s',p',o')** is not contained in **K**.
 * A triple **(s,p,o)(from',to')** is generated. 
   * Timepoint: **from'** is a random year drawn from a gaussian distribution (µ = *from* and *σ² = 5*), **from' = to'**, **from' ≠ from** and **0 < from' ≤ 2013**.
   * Timespan: **from'** is a random year drawn from a gaussian distribution (µ = *from* and *σ² = 2*), the duration **d'** is generated by drawing a random number from a gaussion distribution (μ = *to - from* and *σ² = 5*), **to' = from' + d'**, **0 < d' ≤ 2013**, **from ≠ from'**, **to ≠ to'**, **from ≤ 2013** and **to ≤ 2013**
 * 1/6 of each of the above created negative training sets were randomly selected to create a heterogenous test set. Note that this set contains 780 negative examples.
 
###Folder Structure

<table>
    <tr><td>test</td><td></td><td></td><td></td><td></td><td>contains 7 test sets for testing</td></tr>
    <tr><td>|___</td><td>correct</td><td></td><td></td><td></td><td>contains 750 (10*75) true facts</td></tr>
    <tr><td>|</td><td>|_____</td><td>award</td><td></td><td></td><td></td></tr>
    <tr><td>|</td><td>|_____</td><td>...</td><td></td><td></td><td>other relations ...</td></tr>
    <tr><td>|___</td><td>wrong</td><td></td><td></td><td></td><td>7 different sets with 750 wrong facts each</td></tr>
    <tr><td></td><td>|_____</td><td>domain</td><td></td><td></td><td>the domain of the fact was changed</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td>75 nobel peace prize winners</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td>other relations</td></tr>
    <tr><td></td><td>|_____</td><td>range</td><td></td><td></td><td>...</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td></td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td></td></tr>
    <tr><td></td><td>|_____</td><td>domainrange</td><td></td><td></td><td>...</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td></td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td></td></tr>
    <tr><td></td><td>|_____</td><td>property</td><td></td><td></td><td>...</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td></td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td></td></tr>
    <tr><td></td><td>|_____</td><td>random</td><td></td><td></td><td>...</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td></td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td></td></tr>
    <tr><td></td><td>|_____</td><td>date</td><td></td><td></td><td>...</td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>award</td><td></td><td></td></tr>
    <tr><td></td><td>|</td><td>|_________</td><td>...</td><td></td><td></td></tr>
    <tr><td></td><td>|_____</td><td>mix</td><td></td><td></td><td>contains 13 facts from all test sets of a certain relations, 6 x 13 x 10 = 780 wrong facts</td></tr>
    <tr><td></td><td></td><td>|_________</td><td>domain</td><td></td><td></td></tr>
    <tr><td></td><td></td><td>|</td><td>|_________</td><td>award</td><td></td></tr>
    <tr><td></td><td></td><td>|</td><td>|_________</td><td>...</td><td></td></tr>
    <tr><td></td><td></td><td>|_________</td><td>range</td><td></td><td></td></tr>
    <tr><td></td><td></td><td>|_________</td><td>domainrange</td><td></td><td></td></tr>
    <tr><td></td><td></td><td>|_________</td><td>property</td><td></td><td></td></tr>
    <tr><td></td><td></td><td>|_________</td><td>random</td><td></td><td></td></tr>
    <tr><td></td><td></td><td>|_________</td><td>date</td><td></td><td></td></tr>
    <tr><td>train</td><td></td><td></td><td></td><td></td><td>contains the same folder structure as test</td></tr>
    <tr><td>files</td><td></td><td></td><td></td><td></td><td>files neccessary to create *FactBench*</td></tr>
    <tr><td>|_____</td><td>queries</td><td></td><td></td><td></td><td>DBpedia and Freebase queries</td></tr>
    <tr><td>|_____</td><td>rdf</td><td></td><td></td><td></td><td>surface forms and in/outbound links for wikipedia concepts</td></tr>
    <tr><td>|_____</td><td>freebase</td><td></td><td></td><td></td><td>results of freebase queries (json)</td></tr>
</table>
