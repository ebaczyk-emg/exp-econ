library(readr);
library(moments);
library(ggplot2)

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-02-17-17-29-53/")


#test with PRNG fixed 
setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-14-17-42-32/")

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-15-30-43/")

setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-15-15-56-04/")
setwd("C://Users/Emily/Documents/GitHub/exp-econ-output/2017-03-23-13-53-59/")
rm(list=ls())

nruns <- 100
nagents <- 10
colors <- rainbow(nruns)
equilibrations <- as.vector(rep(0,nruns))
rolling_vol <- data.frame(1:42)
trans <- data.frame(matrix(nrow=(nruns*nagents), ncol=nruns))
bids <- data.frame(matrix(nrow=(nruns*nagents), ncol=nruns))
asks <- data.frame(matrix(nrow=(nruns*nagents), ncol=nruns))

plot(0, xlim= range(0:1000), ylim=range(75:165),xlab = "Transaction #", ylab="Transaction Price",main="Transaction Paths")
abline(a=130,b=0)
for(i in 0:(nruns-1)) {
  transactions <- read.csv(paste("transactions-",i,".csv", sep=""), header = F)
  original_index <- 1:length(transactions$V1)
  transactions <- cbind(original_index, transactions)
  tprices <- (transactions$V3 + transactions$V6)/2
  tprices[transactions$V9=="false"] <- NA
  trans[,(i+1)] <- tprices
  b_temp <- transactions$V3
  b_temp[b_temp==50] <- NA
  bids[,(i+1)] <- b_temp
  a_temp <- transactions$V6
  a_temp[a_temp==300] <- NA
  asks[,(i+1)] <- a_temp
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
      if(price_vol <1 && j>550) {
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

avg_prices <- apply(trans,1,mean,na.rm=T)
points(avg_prices)

plot(0, ylim=range(110:140),xlim=range(0:1000))
avg_bids <- apply(bids, 1, mean, na.rm=T)
avg_asks <- apply(asks, 1, mean, na.rm=T)
points(avg_asks, col="blue")
points(avg_bids, col="red")
points(rolling_price_avg)

rolling_price_avg <- rep(0,(length(avg_prices)))
for(n in 20:length(avg_prices)) {
  rolling_price_avg[n] <- mean(na.omit(avg_prices[(n-19):n]))
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

##intertemporal endowment evolution
level <- data.frame(matrix(nrow=(nruns), ncol=nruns))
delta <- data.frame(matrix(nrow=(nruns), ncol=nruns))
informed <- data.frame(matrix(nrow=(nruns), ncol=nruns))

for(i in 0:(nruns-1)) {
  no_col <- max(count.fields(paste("endowments-",i,".csv", sep=""), sep=","))
  endowments <- read.csv(paste("endowments-",i,".csv", sep=""), na.strings=c(""," ",NA),header=F, flush=T, col.names=1:no_col)
  for(j in 1:100) {
    period_endowments <-endowments[((j-1)*nagents):(j*nagents),]
    level_ags <- subset(period_endowments, period_endowments$X2 == "UninfFwdLevelAgent")
    delta_ags <- subset(period_endowments, period_endowments$X2 == "UninfFwdDeltaAgent")
    informed_ags <- subset(period_endowments, period_endowments$X2 == "InfBckAgent")
    
    level[j,(i+1)] <- mean((apply(!is.na(level_ags),1,sum) - 5)/2)
    delta[j,(i+1)] <- mean((apply(!is.na(delta_ags),1,sum) - 5)/2)
    informed[j,(i+1)] <- mean((apply(!is.na(informed_ags),1,sum) - 5)/2)
  }
}

plot(0, xlim= range(0:100), ylim=range(0:10),xlab = "Time", ylab="Avg. # Assets",main="Intertemporal Endowment Evolution")
for(i in 1:nruns){
  lines(level[,i],col="red")
  lines(delta[,i],col="green")
  lines(informed[,i],col="black")
}
lines(apply(level,1,mean),lwd=4,col="red")
lines(apply(delta,1,mean),lwd=4,col="green")
lines(apply(informed,1,mean),lwd=4,col="black")

lines(apply(level,1,mean) + apply(level,1,sd),col="red")
lines(apply(level,1,mean) - apply(level,1,sd),col="red")
lines(apply(delta,1,mean) + apply(delta,1,sd),col="green")
lines(apply(delta,1,mean) - apply(delta,1,sd),col="green")
lines(apply(informed,1,mean) + apply(informed,1,sd),col="black")
lines(apply(informed,1,mean) - apply(informed,1,sd),col="black")

totals <- apply(level,1,mean) + apply(delta,1,mean) + apply(informed,1,mean)

#find intertemporal valuations
vals_informed <- matrix(nrow=nruns,ncol=nruns)
vals_level <- vals_informed
vals_delta <- vals_informed
plot(0,ylim=range(80:150), xlim=range(0,100))

for(i in 0:(nruns-1)) {
  valuations <- read.csv(paste("valuations-",i,".csv", sep=""), header = F)
  for(j in 1:nruns) {
    period_valuations <-valuations[((j-1)*nagents +1):(j*nagents),]
    vals_informed[j,(i+1)] <- mean(subset(period_valuations, period_valuations$V2 == "InfBckAgent")[,3])
    vals_level[j,(i+1)] <- mean(subset(period_valuations, period_valuations$V2 == "UninfFwdLevelAgent")[,3])
    vals_delta[j,(i+1)] <- mean(subset(period_valuations, period_valuations$V2 == "UninfFwdDeltaAgent")[,3])
  }
}
lines(apply(vals_informed, 1, mean), col="black")
lines(apply(vals_delta,1,mean), col="green")
lines(apply(vals_level,1,mean), col="blue")
