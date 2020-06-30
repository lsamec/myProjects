- virtual_liga_kosarka - virtual league basketball web application; uses Maven, Spring, JSP, Bootstrap, H2 database
						(regards to my college team)
						
- grass_eaters - graphic demonstration of genetic algorithm; it is imitation of http://math.hws.edu/eck/jsdemo/jsGeneticAlgorithm.html

- NSGA2 - multi-objective optimization genetic algorithm implementation; https://ieeexplore.ieee.org/document/996017

- dipl - deep reinforcement learning for simple computer game; uses Keras, game is imitation of https://docs.riddles.io/light-riders/rules

- SoundDigitClassifier - spoken digits recognition using deep learning; uses Tensorflow, dataset from https://github.com/Jakobovski/free-spoken-digit-dataset
						(simple classification problem with preprocessing specific to audio analysis)
						
- competitive_informaitcs_tasks - solutions to problems from SPOJ, GeeksForGeeks etc. 
								(my SPOJ profile https://www.spoj.com/users/lsamec/)

- LRWithNumGD - simple linear regression with numeric gradient descent

- recommender - simple recommender system


My take on certain CS questions:

Is P != NP ?

The assumption is that P != NP can be boiled down to question whether finding an answer has same complexity
as checking whether the answer is right.
Lets imagine a phonebook with only numbers without names. We want to find which answer belongs to Mark (only one answer belongs to Mark)
For that we need to call every number in phonebook and see whether Mark will answer the call.
That is the "finding an answer" case.
Lets imagine that we have a number a want to check whether the answer belongs to mark.
For that we need to call that number and see whether Mark will answer.
That is the "checking the answer" case.

We can clearly see that first case includes second case (it includes it multiple times)
therefore first case has bigger complexity that second case therefore they don't have the same complexity.

So, conclusion is that finding an answer doesn't have the same complexity as checking an answer
therefore it is really true that P != NP

This proof is valid if the assumption at the beginning is true.



Interpolation vs. extrapolation?

I claim that usually word extrapolation shouldn't be used.
Lets imagine a dot of valid information in the middle of 2D information plane.
As we move further from the dot the validity of information decreases proportionally to the distance we moved away from it.
Now, lets put another dot of information somewhere in the plain.
The information of two dots constructively interferes.
So, our best guess based on the amount of information we have is that on the line connecting these two dots.
So, we guess the information value on the line between the dots and that can be called interpolation.
Only case when word extrapolation might be used is when we have only one dot and try to assume the information
value around it. I claim that this case will never appear in real world. We always try to find more than one piece of information.
Extrapolation cound be used when we are talking about that deduction. 
We could say that when we are deducing something we are moving away from the dot of information.
When there are at least two dots information is always interfering and therefore I claim that interpolation is proper word for assuming information value in that case. 
 









	


