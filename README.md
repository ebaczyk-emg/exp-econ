# exp-econ
Experimental Economics Final Project

This project attempts to examine the formation of bubbles in heterogeneous and 
homogeneous experimental asset markets.  Populations of agents will be generated that 
differ in the following ways: 

1) Price Brain: agents can price assets by looking at either the price levels prevailing 
in the market, the change in price levels (first derivative), or the change in price
changes (second derivative)

2) Induction Brain: agents can price assets by using either forward or backwards
inductive tools.  In other words, they can use previous pricing data or they can 
make inferrences about future states of the world

3) Level of Thought Brain: agents can work entirely with their own information, 
or they can assume any number of "levels of thought" with regards to their competitors.
Particularly, they can form the best response to the ideally rational agent, or only
assume it capable of limited thought itself.  

These are the first concepts that will be explored.  This repository will contain the 
necessary code to output market function and will contain separate code that attempts to 
analyze this data.  The former will be written in Java, the latter in R. 
