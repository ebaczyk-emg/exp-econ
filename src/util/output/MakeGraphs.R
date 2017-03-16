library(readr);
library(moments) ;

# read in the csv

#04-21-56-58
#file:///C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-04-15-47-29
setwd("C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-08-02-39-20")
setwd("C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-08-02-02-09")
setwd("C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-08-01-24-54")
setwd("C:/Users/Emily/Documents/GitHub/exp-econ-output/2016-12-08-10-22-19") #bubble
setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-16-13-44/")


rm(list=ls())

transactions <- read.csv("transactions.csv", header = F)
transactions <- as.data.frame(transactions)
transactions$V9 <- transactions$V9  == "true"
full_index <- 1:dim(transactions)[1]
full_index <- as.data.frame(full_index)
transactions <- cbind(full_index, transactions)
#just_trans <- transactions[transactions$v5 == TRUE,]
jt <- subset(transactions, transactions$V9 == T)
jt <- jt[1:100,]
index <- 1:dim(jt)[1]
index <- as.data.frame(index)
jt <- cbind(index, jt)
plot(jt$V6)

infBck <-     subset(jt, jt$V5=="InfBckAgent")
uninfLevel <- subset(jt, jt$V5=="UninfFwdLevelAgent")
uninfDelta <- subset(jt, jt$V5=="UninfFwdDeltaAgent")

plot(0, xlim= range(jt$index), ylim=range(jt$V3),xlab = "Transaction #", ylab="Transaction Price",main="Buyers")
points(infBck$index, infBck$V3, col = c('red'))
points(uninfLevel$index, uninfLevel$V3, col = c('blue'))
points(uninfDelta$index, uninfDelta$V3, col = c('forestgreen'))
legend(150, 120, c("Informed", "Uninformed Level", "Uninformed Delta"), lty = c(1,1,1), col=c("red","blue","forestgreen"), lwd=c(2.5, 2.5,2.5),bty = "n")

infBck_s <-     subset(jt, jt$V8=="InfBckAgent")
uninfLevel_s <- subset(jt, jt$V8=="UninfFwdLevelAgent")
uninfDelta_s <- subset(jt, jt$V8=="UninfFwdDeltaAgent")

plot(0, xlim= range(jt$index), ylim=range(jt$V4),xlab = "Transaction #", ylab="Transaction Price",main="Sellers")
points(infBck_s$index, infBck_s$V4, col = c('red'))
points(uninfLevel_s$index, uninfLevel_s$V4, col = c('blue'))
points(uninfDelta_s$index, uninfDelta_s$V4, col = c('forestgreen'))
legend(250, 120, c("Informed", "Uninformed Level", "Uninformed Delta"), lty = c(1,1,1), col=c("red","blue","forestgreen"), lwd=c(2.5, 2.5,2.5))

plot(0, xlim= range(jt$index), ylim=range(75:150),xlab = "Transaction #", ylab="Transaction Price",main="Transactions (inner = Seller, outer = Buyer)")
points(infBck$index, infBck$V3, col = c('red'), pch=46, cex=10)
points(uninfLevel$index, uninfLevel$V3, col = c('blue'), pch=46, cex=10)
points(uninfDelta$index, uninfDelta$V3, col = c('green'), pch=46, cex=10)

points(infBck_s$index, infBck_s$V3, col = c('red'), pch=46, cex=5)
points(uninfLevel_s$index, uninfLevel_s$V3, col = c('blue'), pch=46, cex=5)
points(uninfDelta_s$index, uninfDelta_s$V3, col = c('green'), pch=46, cex=5)
legend(60, 110, c("Informed", "Uninformed Level", "Uninformed Delta"), lty = c(1,1,1), col=c("red","blue","forestgreen"), lwd=c(2.5, 2.5,2.5))



no_trans <- subset(transactions, transactions$V7 == F)
no_inf <- subset(no_trans, no_trans$V3=="InfBckAgent")
no_level <- subset(no_trans, no_trans$V3=="UninfFwdLevelAgent")
no_delta <- subset(no_trans, no_trans$V3=="UninfFwdDeltaAgent")
tr_infBck <- subset(jt, jt$V3=="InfBckAgent")
tr_uninfLevel <- subset(jt, jt$V3=="UninfFwdLevelAgent")
tr_uninfDelta <- subset(jt, jt$V3=="UninfFwdDeltaAgent")



plot(0, xlim= range(transactions$full_index), ylim=range(transactions$V4),xlab = "Transaction #", ylab="Transaction Price",main="Sample Transaction Path")
points(no_level$full_index, no_level$V4)



##stats

max_drawdown <- function(returns) {
  local_max <- 0
  local_min <- 0
  dd_time <- 1
  largest_drawdown <- 0
  current_drawdown <- 0
  for(i in 1 : length(returns)) {
    if(!is.na(returns[i])){
      if(returns[i] > local_max) {
        local_max <- returns[i]
        time_of_max <- i
      } else if (returns[i] < local_min) {
        local_min <- returns[i]
        time_of_dd <- i - time_of_max
        current_drawdown <- local_max - local_min
      }
      
      if(current_drawdown > largest_drawdown){
        largest_drawdown <- current_drawdown
        dd_time <- time_of_dd
      }
    }
  }
  output <- t(list(largest_drawdown, dd_time))
  return(output)
}


