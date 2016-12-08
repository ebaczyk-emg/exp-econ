library(readr);
library(moments) ;

# read in the csv
setwd("C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-05-23-38-10")
#04-21-56-58
#file:///C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-04-15-47-29
rm(list=ls())

transactions <- read.csv("transactions.csv", header = F)
transactions <- as.data.frame(transactions)
transactions$V7 <- transactions$V7  == "true"
full_index <- 1:dim(transactions)[1]
full_index <- as.data.frame(full_index)
transactions <- cbind(full_index, transactions)
#just_trans <- transactions[transactions$v5 == TRUE,]
jt <- subset(transactions, transactions$V7 == T)
index <- 1:dim(jt)[1]
index <- as.data.frame(index)
jt <- cbind(index, jt)
plot(jt$V4)

infBck <- subset(jt, jt$V6=="InfBckAgent")
uninfLevel <- subset(jt, jt$V6=="UninfFwdLevelAgent")
uninfDelta <- subset(jt, jt$V6=="UninfFwdDeltaAgent")

plot(0, xlim= range(jt$index), ylim=range(jt$V4),xlab = "Transaction #", ylab="Transaction Price",main="Sample Transaction Path")
points(infBck$index, infBck$V4, col = c('red'))
points(uninfLevel$index, uninfLevel$V4, col = c('blue'))
points(uninfDelta$index, uninfDelta$V4, col = c('forestgreen'))
legend(30, 120, c("Informed", "Uninformed Level", "Uninformed Delta"), lty = c(1,1,1), col=c("red","blue","forestgreen"), lwd=c(2.5, 2.5,2.5))

no_trans <- subset(transactions, transactions$V7 == F)
no_inf <- subset(no_trans, no_trans$V3=="InfBckAgent")
no_level <- subset(no_trans, no_trans$V3=="UninfFwdLevelAgent")
no_delta <- subset(no_trans, no_trans$V3=="UninfFwdDeltaAgent")
tr_infBck <- subset(jt, jt$V3=="InfBckAgent")
tr_uninfLevel <- subset(jt, jt$V3=="UninfFwdLevelAgent")
tr_uninfDelta <- subset(jt, jt$V3=="UninfFwdDeltaAgent")



plot(0, xlim= range(transactions$full_index), ylim=range(transactions$V4),xlab = "Transaction #", ylab="Transaction Price",main="Sample Transaction Path")
points(no_level$full_index, no_level$V4)



