The purpose of this challenge is to see how you solve problems while
managing objectives that often seem contradictory.

Simpli.fi processes hundreds of thousands of requests every second in
datacenters across the globe.  Because of this, we have to have a
system that's very performant.  On the other hand, one way that we maintain a
competetive edge is by being able to adapt to meet a huge variety of
clients' needs.  Therefore, our system has to also be maintainable,
adaptable, and manageable.  We must also be very fast-paced.  The
ability to tweak the system on short notice can mean the difference
between keeping or losing hundreds of thousands of dollars worth of
contracts. 

For this challenge, we want you to implement a simplified version of
our matching process.  We get bidding requests that look like:

http://simpli.fi/ck_bid?kw=mazda+cars&ad_size=320x250&ip=67.10.32.95&user_agent=Mozilla%2f5.0%20%28compatible%3b%20MSIE%209.0%3b%20Windows%20NT%206.0%3b%20Trident%2f5.0%29

Our matching process must determine, based on this ad request, if we
have any ads for which it makes sense to serve at that moment to the
user who generated that request.

There are three parts to this challenge.

######################################################################
#  Part I

In the requests.log file attached, you'll find 10,000 bid requests of
the form above.  In the ads.txt file, you'll find specifications for
1,000 ads.  The format of the ad specification is

1234, 320, 250, 1.0, [mazda, 0.0, cars, 0.5, mazda3, 1.5]

where the first field is the ad id, the second field is the width of
the ad in pixels, the third field is the height of the ad in pixels,
the fourth field is the default bid for the ad, and the fifth field is
an array that contains pairs of keywords and bids for those keywords.
If a keyword has a bid of 0.0, then the ad's default bid should be
used.  In the example above, the first keyword is "mazda" and the
corresponding bid is 0.0, so the campaign bid of 1.0 should be used.
The second keyword is "cars" with a bid of 0.5, and the third keyword
is "mazda3" with a bid of 1.5.  You may assume that keywords consist
of a single word with no spaces (but possibly other characters).

Each ad has a maximum bid amount.  If more than one ad matches, the one with
the highest bid should be returned.  The request above has "mazda" and "cars"
as keywords; the bid should be 1.0 because "mazda" has the default bid.

Your code should be written in C++ and should

1) Load the ads from ads.txt
2) Read requests from standard input (we're not asking you to
implement a web server!). 
3) Attempt to match each request with an ad.  
    a) The size of the ad should match exactly.
    b) At least one of the keywords match.
    c) If a match is found, print out a string with the format 
          "12345, 1.5"
       where 12345 is the id of the matching ad and 1.5 is the
       bid value for the highest-valued matching keyword.
    d) If no match is found, print out "0, 0.0"
    e) Print one match per line of output

You can use whatever external libraries that you see fit (so long as they are
also available to us), and there no restrictions on the format of your code or
compilation except that it should be in C++ and we should be able to compile it
and then run it by doing something like

cat requests.log | ./your_program > output.txt

######################################################################
# Part II

We work with several exchanges, and each one sends us bid requests in
a different format.  Using your code from Part I as a starting point, add
the ability to handle requests that take the form

http://simpli.fi/kal_el?keywords=cars+mazda&ad_width=320&ad_height=250&user_ip=67.10.32.95&browser_agent=Mozilla%2f5.0%20%28compatible%3b%20MSIE%209.0%3b%20Windows%20NT%206.0%3b%20Trident%2f5.0%29

######################################################################
# Part III

Now suppose a client wants to serve an ad only to iPad users.  

You do not have to implement a solution to this problem (though feel
free to write code if you want), but please write a few words about
how you would handle this modification to the problem.  What
assumptions have you made in your code to this point that would no
longer be valid?  What would you change?  How would your changes
impact performance?
