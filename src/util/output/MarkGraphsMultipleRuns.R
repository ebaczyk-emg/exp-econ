library(readr);
library(moments);

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-02-17-17-29-53/")

rm(list=ls())

nruns = 100
colors <- rainbow(nruns)
equilibrations <- as.vector(rep(0,nruns))
rolling_vol <- data.frame(1:42)

plot(0, xlim= range(0:60), ylim=range(75:200),xlab = "Transaction #", ylab="Transaction Price",main="Transaction Paths")

for(i in 0:(nruns-1)) {
  transactions <- read.csv(paste("transactions-",i,".csv", sep=""), header = F)
  jt <- subset(transactions, transactions$V9 == "true")
  index <- 1:length(jt$V3)
  jt <- cbind(index, jt)
  lines(jt$index, jt$V3, col=colors[i])
  
  length_rvol <- max(0,(length(jt$V3) -10))
  rvol <- 0:(length_rvol-1)
  for(j in 10:length(jt$V3)) {
    price_vol <- sd(jt$V3[(j-10):j])
    rvol[(j-10)] <- price_vol
    if(price_vol <1) {
      equilibrations[i] <- jt$V3[j]
      break
    }
  }
  
  
  for(j in 10:length(jt$V3)) {
    price_vol <- sd(jt$V3[(j-10):j])
    rvol[(j-10)] <- price_vol
  }
  rolling_vol <- qpcR:::cbind.na(rolling_vol, rvol)
}

#plot rolling vols

plot(0, xlim= range(0:50), ylim=range(1:30),xlab = "rolling vols", ylab="volatilities",main="Transaction Paths")
for(i in 1:nruns) {
  lines(rolling_vol[,(i+1)], col=colors[i])
}

hist(equilibrations[equilibrations>0], breaks=40)






