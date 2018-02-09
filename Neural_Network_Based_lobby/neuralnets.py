import numpy as np

class BackPropagationNetwork:
	"""This is a backpropagating training neural network"""
	#identifiers
	# 
	layerCount=0
	shape=None
	weights=[]
	# 
	# class members
	# 
	def __init__(self,layerSize):
		"""Initialize the network"""

		# Layer info
		self.layerCount=len(layerSize)-1
		self.shape=layerSize

		# Data from last run
		self.layerInput=[]
		self.layerOutput=[]
		self.previousWeightDelta=[]

		# Create the weight arrays
		for (l1,l2) in zip(layerSize[:-1],layerSize[1:]):
			self.weights.append(np.random.normal(scale=0.16,size=(l2,l1+1)))
			self.previousWeightDelta.append(np.random.normal(scale=0.16,size=(l2,l1+1)))

	# 
	# Run method
	# 
	def Run(self,input):
		"""Run the network based on Inputs"""
		lnCases=input.shape[0]

		# Clear out the previous intermediate value lists
		self.layerInput=[]
		self.layerOutput=[]

		# Run loop
		for index in range(self.layerCount):
			 # Determine layer input
			 if index==0:
			 	layer_Input=self.weights[0].dot(np.vstack([input.T,np.ones([1,lnCases])]))
			 else:
			 	layer_Input=self.weights[index].dot(np.vstack([self.layerOutput[-1],np.ones([1,lnCases])]))

			 self.layerInput.append(layer_Input)
			 self.layerOutput.append(self.sgm(layer_Input))

		return self.layerOutput[-1].T
	# 
	# TrainEpoch method
	#
	def TrainEpoch(self,input,target,trainingRate=0.07,momentum=0.5):
		"""This method trains the network for one epoch"""
		delta=[]
		lnCases=input.shape[0]

		# First run the network
		self.Run(input)

		# Calculate our deltas
		for index in reversed(range(self.layerCount)):
			if index==self.layerCount-1:
				# Compare to the target values
				# print self._layerOutput[index]
				output_delta=self.layerOutput[index]-target.T
				error=np.sum(output_delta**2)
				delta.append(output_delta*self.sgm(self.layerInput[index],True))
			else:
				# Compare to the following layer's delta
				delta_pullback=self.weights[index+1].T.dot(delta[-1])
				delta.append(delta_pullback[:-1, :]*self.sgm(self.layerInput[index],True))

		# Compute the weight deltas
		for index in range(self.layerCount):
			delta_index=self.layerCount-1-index
			if index==0:
				layer_Output=np.vstack([input.T,np.ones([1,lnCases])])
			else:
				layer_Output=np.vstack([self.layerOutput[index-1],np.ones([1,self.layerOutput[index-1].shape[1]])])
			curWeightDelta=np.sum(layer_Output[None,:,:].transpose(2,0,1)*delta[delta_index][None,:,:].transpose(2,1,0), axis=0)
			weightDelta=trainingRate*curWeightDelta+momentum*self.previousWeightDelta[index]
			self.weights[index]-=weightDelta
			self.previousWeightDelta[index]=weightDelta

		return error



	# Transfer function
	def sgm(self,x,Derivative=False):
		if not Derivative:
			return 1/(1+np.exp(-x))
		else:
			der=self.sgm(x)
			return der*(1-der)

