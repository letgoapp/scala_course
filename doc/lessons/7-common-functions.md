
# Common Functions

TODO


# Exercise

## First quest

We're going to implement a new feature in our slack bot!

The goal is to build a bot that suggests deploying repositories whenever a new pull request is merged.

How will we achieve this? We will monitor messages that look like this:

![Github closed PR image](http://image.prntscr.com/image/be13c07e96a0472bbe27ca82d9d09bf6.png)

After a message like this has been detected, our bot will then suggest a deploy, like this:

![Deploy suggestion image](http://image.prntscr.com/image/73fe7cc980634701a2f36e351d09f54a.png)

So that the user can then deploy (or not).

### Show me the code

Our lazy engineers have started implementing the code but now have gone on strike so we need some help in order to
finish this feature on time.

They have almost finished implementing the `RepositoriesWithPullRequestClosedUseCase` use case, but it's still missing
something.

You can test it using `testOnly *RepositoriesWithPullRequestClosedUseCaseTest`, which should be failing right now because the
full implementation is not done.


## Second quest: Hackathon!

After you have finished what our engineers have left undone (they will be fired, don't worry), you now have
time to implement another feature of your own!

Some ideas:

* Make the bot listen for a certain command and then send a message in response (for example, listening to /joke and then telling a joke)
* Make the bot listen for your name and then send a message in response

Take a look at how the already implemented feature is done, so you can reuse code ;).

Good luck!

