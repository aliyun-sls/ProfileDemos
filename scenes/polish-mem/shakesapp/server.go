// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Package shakesapp defines a server which can be queried to determined
// many times a string appears in the works of Shakespeare, and a client
// which can be used to send load to that server.
package shakesapp

import (
	"bytes"
	"context"
	"fmt"
	"io/ioutil"
)

// server is an implementation of the server for ShakespeareService (defined
// in shakesapp.proto).
type server struct{}

// NewServer returns an implementation of the server for ShakespeareService
// (defined in shakesapp.proto).
func NewServer() ShakespeareServiceServer {
	return &server{}
}

// GetMatchCount implements a server for ShakespeareService.
func (s *server) GetMatchCount(ctx context.Context, req *ShakespeareRequest) (*ShakespeareResponse, error) {
	resp := &ShakespeareResponse{}
	data, err := ioutil.ReadFile("data.log")
	if err != nil {
		return resp, fmt.Errorf("fails to read files: %s", err)
	}
	query := []byte(req.Query)

	for {
		idx := bytes.Index(data, []byte{'\n'})
		var temp []byte
		if idx == -1 {
			temp = data
		} else {
			temp = data[:idx]
		}
		if bytes.Contains(temp, query) {
			resp.MatchCount++
		}
		if idx == -1 {
			break
		}
		data = data[idx+1:]
	}
	return resp, nil
}
