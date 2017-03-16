library(readr);
library(moments);
library(ggplot2)

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-02-17-17-29-53/")


#test with PRNG fixed 
setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-14-17-42-32/")

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-15-30-43/")

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-15-56-04/")
setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-16-27-48/")
rm(list=ls())

nruns <- 100
nagents <- 15
colors <- rainbow(nruns)
equilibrations <- as.vector(rep(0,nruns))
rolling_vol <- data.frame(1:42)
trans <- data.frame(matrix(nrow=(nruns*nagents), ncol=nruns))

plot(0, xlim= range(0:1500), ylim=range(75:165),xlab = "Transaction #", ylab="Transaction Price",main="Transaction Paths")

for(i in 0:(nruns-1)) {
  transactions <- read.csv(paste("transactions-",i,".csv", sep=""), header = F)
  original_index <- 1:length(transactions$V1)
  transactions <- cbind(original_index, transactions)
  tprices <- (transactions$V3 + transactions$V6)/2
  tprices[transactions$V9=="false"] <- NA
  trans[,(i+1)] <- tprices
  jt <- subset(transactions, transactions$V9 == "true")
  index <- 1:length(jt$V3)
  jt <- cbind(index, jt)
  lines(jt$original_index, (jt$V6 + jt$V3)/2, col=colors[i])
  
  if(length(jt$V3)<=10) {
    equilibrations[(i+1)] <- NA
  } else {
    length_rvol <- max(0,(length(jt$V3) -10))
    rvol <- 0:(length_rvol-1)
    for(j in 10:length(jt$V3)) {
      price_vol <- sd(jt$V3[(j-10):j])
      rvol[(j-10)] <- price_vol
      if(price_vol <1) {
        equilibrations[(i+1)] <- jt$V3[j]
        break
      }
    }
  
    rvol <- 1:(length(jt$v3)-10)
    for(j in 10:length(jt$V3)) {
      rvol[(j-10)] <- sd(jt$V3[(j-10):j])
    }
    rolling_vol <- qpcR:::cbind.na(rolling_vol, rvol)
  }
}

#plot rolling vols

plot(0, xlim= range(0:100), ylim=range(1:30),xlab = "rolling vols", ylab="volatilities",main="Transaction Paths")
for(i in 1:nruns) {
  lines(rolling_vol[,(i+1)], col=colors[i])
}

hist(equilibrations[equilibrations>0], breaks=40)

#plot spreads for a single run
riq <- transactions
index <- 1:length(riq$V1)
riq <- cbind(index, riq)
no_defs <- subset(riq, (riq$V3!=50) & (riq$V6!=300))
plot(0, xlim=range(0:length(no_defs$V1)), ylim = range(0:150))
points(no_defs$index, no_defs$V3, pch=1, col="grey")
points(no_defs$index, no_defs$V6, pch=1, col="blue")
no_defs_t <- subset(no_defs, no_defs$V9=="true")
points(no_defs_t$index, (no_defs_t$V3 + no_defs_t$V6)/2, pch=20, col="red")

df <- data.frame(no_defs)
tprices <- (df$V3 + df$V6)/2
tprices[df$V9=="false"] <- NA

df <- cbind(df, tprices)
df <- data.frame("index"=no_defs$index, "mid"=(no_defs$V3+ no_defs$V6)/2)
p <- ggplot(df, aes(index, V3), col=c("grey")) +geom_point() + theme_classic()
ggExtra::ggMarginal(p, type=c("histogram"), margins=c("x"))


p1 <- ggplot(df, aes(x=index, y = index)) +theme_classic()+ geom_point(aes(x=df$index, y=df$V3), color="blue")   + geom_point(aes(x=df$index, y=df$V6), color="grey");
ggExtra::ggMarginal(p1, type=c("histogram"), margins=c("x"))

ggExtra::ggMarginal(data=df, x="index", y="index", type=c("histogram"), margins=c("x"))

df_trimmed <- data.frame("index" =df$index, "tprices"=df$tprices)
df_trimmed <- df_trimmed[!is.na(df_trimmed$tprices),]
p2 <- ggplot(df_trimmed, aes(index, tprices)) + geom_point()
ggExtra::ggMarginal(p2, type=c("histogram"), margins=c("x"), binwidth=50)

#plotting number of transactions
ntransactions <- rowSums(!is.na(trans))
barplot(ntransactions)
smoothed_trans <- 1:15
for(k in 1:15) {
  smoothed_trans[k] <- sum(ntransactions[((k-1)*100):(k*100)])
}
plot(smoothed_trans)
