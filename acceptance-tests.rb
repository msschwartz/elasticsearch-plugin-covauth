require 'httparty'
require 'json'
require 'yaml'
require 'base64'

# load config from elasticsearch server
config = YAML.load_file(__dir__ + '/elasticsearch/config/elasticsearch.yml')
user = config["covauth"]["clientId"]
pass = config["covauth"]["clientSecret"]
auth = 'Basic ' + Base64.encode64(user + ':' + pass).chomp

# retrieve access token from api service
response = HTTParty.post(config["covauth"]["authServiceBaseUrl"],
  :body    => nil,
  :headers => {
    "Authorization" => auth,
    "Accept" => "application/vnd.com.covisint.platform.oauth.token.v1+json",
    "Type" => "client_credentials"
  }
)
data = JSON.parse(response.body)
p data
access_token = data["access_token"]
